package com.home.webdev.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.home.webdev.entities.User;
import com.home.webdev.service.UserService;
import com.home.webdev.util.SecurityUtil;

@Controller
@RequestMapping({"/{locale:en|vn}/user","/user"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        model.addAttribute("roles", userService.findAllRoles());
        
        return "./user/registration";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST , produces = "text/plain;charset=UTF-8")
    public String saveUser(@Valid User user, BindingResult result,ModelMap model) {//BindingResult result,

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
        
        //return "success";
        return "./user/registrationsuccess";
    }


    @RequestMapping(value = "/edit-user-{userName}", method = RequestMethod.GET)
    public String editUser(@PathVariable String userName, ModelMap model) {
        User user = userService.findByUserName(userName);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        model.addAttribute("roles", userService.findAllRoles());
        
        return "./user/registration";
    }

    @RequestMapping(value = "/edit-user-{userName}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String userName) {

        if (result.hasErrors()) {
            return "/user/registration";
        }

        userService.updateUser(userName, user);
        model.addAttribute("edit", true);
        model.addAttribute("success", user.getFullname());
        model.addAttribute("loggedinuser", SecurityUtil.getPrincipal());
        return "/user/registrationsuccess";
    }

    @RequestMapping(value = "/delete-user-{ssoId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUser(ssoId);
        return "redirect:/admin/list";
    }
}
