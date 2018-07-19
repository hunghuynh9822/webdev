package com.home.webdev.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
