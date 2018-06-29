package com.home.webdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.home.webdev.service.UserService;
import com.home.webdev.util.SecurityUtil;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
    private UserService userService;
	
    @RequestMapping(value = {"/{locale:en|vn|}/home","/","/{locale:en|vn}/","/home" }, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/home");
        model.addObject("user", userService.findByUserName(SecurityUtil.getPrincipal()));
        return model;
    }
    
    
}
