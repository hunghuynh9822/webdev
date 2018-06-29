package com.home.webdev.dao.impl;

import org.springframework.stereotype.Repository;

import com.home.webdev.dao.generic.GenericDaoImpl;
import com.home.webdev.dao.inter.RoleDao;
import com.home.webdev.entities.Role;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer>
							implements RoleDao{

}
