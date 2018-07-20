package com.home.webdev.dao.inter;

import com.home.webdev.dao.generic.GenericDao;
import com.home.webdev.entities.User;

public interface UserDao extends GenericDao<User, String>{
	User getByUsernameOrEmail(String username);
}
