package com.home.webdev.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.home.webdev.dao.generic.GenericDaoImpl;
import com.home.webdev.dao.inter.UserDao;
import com.home.webdev.entities.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, String>
							implements UserDao{

	public UserDaoImpl() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public User getByUsernameOrEmail(String username) {
		List<User> userList = new ArrayList<User>(); 
        Query query = getSession().createQuery("from User u where u.username = :name or u.email = :name");
        query.setParameter("name", username);
        userList = query.list();  
        if (userList.size() > 0)  
            return userList.get(0);  
        else  
            return null; 
	}
	

}
