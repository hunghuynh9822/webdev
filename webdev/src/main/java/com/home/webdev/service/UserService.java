package com.home.webdev.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.home.webdev.dao.impl.RoleDaoImpl;
import com.home.webdev.dao.impl.UserDaoImpl;
import com.home.webdev.dao.inter.RoleDao;
import com.home.webdev.dao.inter.UserDao;
import com.home.webdev.entities.Role;
import com.home.webdev.entities.User;

@Transactional
@Service
public class UserService {
	@Autowired
	private UserDaoImpl dao;

	@Autowired
	private RoleDaoImpl roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByUserName(String userName) {
		User user = dao.getByKey(userName);
		return user;
	}
	
	public User findByUserNameOrEmail(String username)
	{
		User user = dao.getByUsernameOrEmail(username);
		return user;
	}
	
	public void isAdmin(User user)
	{
		user.setRole(roleDao.getByKey(1));
	}

	// public User findByUserName(String userName, boolean withRoles) {
	// User user = dao.getByKey(userName);
	// if (withRoles) {
	// user.getRoles().size(); // load user roles
	// }
	// return user;
	// }

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.create(user);
	}

	public void updateUser(String userName, User user) {
		User entity = dao.getByKey(userName);
		if (entity != null) {
			if (!user.getPassword().equals(entity.getPassword())) {
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFullname(user.getFullname());
			entity.setRole(user.getRole());
		}
	}

	public List<User> findAllUsers() {
		return dao.getAll();
	}

	public boolean isUserUnique(String userName) {
		User user = dao.getByKey(userName);
		return user == null;
	}

	public void deleteUser(String userName) {
		User entity = dao.getByKey(userName);
		if (entity != null) {
			dao.delete(entity);
		}
	}

	public List<Role> findAllRoles() {
		return roleDao.getAll();
	}

	public Role getRoleById(Integer roleId) {
		return roleDao.getByKey(roleId);
	}

}
