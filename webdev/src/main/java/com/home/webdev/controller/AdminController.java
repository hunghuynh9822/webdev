package com.home.webdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.home.webdev.entities.User;
import com.home.webdev.service.UserService;

@Controller
@RequestMapping({"/{locale:en|vn}/admin","/admin"})
public class AdminController {
	@Autowired
    private UserService userService;
	
	@RequestMapping(value = {"/list" }, method = RequestMethod.GET)
    public ModelAndView listUsers() {
        ModelAndView model = new ModelAndView();
        model.setViewName("./admin/list");

        List<User> users = userService.findAllUsers();
        model.addObject("users", users);
        
        return model;
    }
}
