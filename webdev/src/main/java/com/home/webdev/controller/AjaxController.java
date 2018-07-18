package com.home.webdev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.webdev.entities.User;
import com.home.webdev.model.AjaxResponseBody;
import com.home.webdev.model.SearchCriteria;
import com.home.webdev.service.UserService;

@RestController
@RequestMapping({"/"})
public class AjaxController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/test")
	public String printWelcome(ModelMap model) {
		return "welcome";
	}


	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by
	// field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.

	
	//@JsonView(Views.Public.class)
	@RequestMapping(value = "/search/api/getSearchResult")
	public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {
		AjaxResponseBody result = new AjaxResponseBody();

		if (search != null) {
			List<User> users = new ArrayList<User>();
			User user = userService.findByUserName(search.getUsername());
			if (user != null) {
				users.add(user);

				result.setCode("200");
				result.setMsg("");
				result.setResult(users);
			} else {
				result.setCode("204");
				result.setMsg("No user!");
			}

		} else {
			result.setCode("400");
			result.setMsg("Search criteria is empty!");
		}

		// AjaxResponseBody will be converted into json format and send back to client.
		return result;
	}

}
