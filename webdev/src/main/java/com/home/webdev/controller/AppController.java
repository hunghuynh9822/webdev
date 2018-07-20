package com.home.webdev.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.home.webdev.entities.User;
import com.home.webdev.service.UserService;
import com.home.webdev.util.SecurityUtil;

@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class AppController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        //model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        model.addAttribute("currentUser", userService.findByUserName(SecurityUtil.getPrincipal()));
        return "./accessDenied";
    }


    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = { "/{locale:en|vn}/login","/login"})
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "/login";
        } else {
            return "redirect:/home";
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/home";
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        model.addAttribute("roles", userService.findAllRoles());
        
        return "./user/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,ModelMap model, HttpServletRequest request) {//BindingResult result,

        if (result.hasErrors()) {
        	model.addAttribute("user", user);
            return "/user/registration";
        }

        if(!userService.isUserUnique(user.getUsername())){
        	model.addAttribute("user", user);
            FieldError ssoError =new FieldError("user","username", messageSource.getMessage("user.unique.userName", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(ssoError);
            return "./user/registration";
        }
        if(user.getUsername().equals("admin"))
        {
        	userService.isAdmin(user);
        }
        userService.saveUser(user);

        model.addAttribute("success", user.getFullname()	);
        model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        
        //Login
        try {
			request.login(user.getUsername(), user.getPassword());
		} catch (ServletException e) {
			e.printStackTrace();
		}
        
        //return "success";
        return "./user/registrationsuccess";
    }
    
    //Upload file
    
    private String UPLOAD_FOLDER = "D:/upload/";
    
    @RequestMapping(value = { "/uploadfile" }, method = RequestMethod.GET)
	public String onload() {
		return "upload";
	}
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(ModelMap model, HttpServletRequest request, HttpServletResponse response,
	            @RequestParam(value = "fileUpload", required = false) MultipartFile fileUpload) {
	    //String path = request.getSession().getServletContext().getRealPath("/") + "resources/uploads/";
	    if (fileUpload != null) {
	        try {
	        	String fileName = new Date().getTime() + "-"+ fileUpload.getOriginalFilename(); // 1234567898-filename.jpg
	             File upload = new File(UPLOAD_FOLDER + fileName);
	             fileUpload.transferTo(upload);
	             
	             
	             model.addAttribute("fileUpload", fileName);
	             return "upload-success";
	        } catch (IOException ex) {
	             ex.printStackTrace();
	        }
	    }
	    
	    // tao file jsp error
	    return "upload-error";
	}
	
	/*
     * Download a file from 
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere. 
     */
    @RequestMapping(value="/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {
     
    	String filePath = UPLOAD_FOLDER + fileName;
        File file = new File(filePath);
         
        
        // Return message error if file not exist
        // or return default image
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
