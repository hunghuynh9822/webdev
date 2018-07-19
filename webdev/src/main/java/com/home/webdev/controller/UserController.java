package com.home.webdev.controller;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
