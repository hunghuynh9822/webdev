package com.home.webdev.dao.impl;

import org.springframework.stereotype.Repository;

import com.home.webdev.dao.generic.GenericDaoImpl;
import com.home.webdev.dao.inter.UserDao;
import com.home.webdev.entities.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, String>
							implements UserDao{

}
