package com.home.webdev.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericDaoImpl<T,K extends Serializable> implements GenericDao<T, K>{
	
	private Class<T> entityClass;
	
	public GenericDaoImpl(Class<T> entityClass){
        this.entityClass = entityClass;
    }
	
	private GenericDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(entityClass);
	}
	
	@Override
	public void create(T entity) {
		getSession().persist(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
		
	}

	@Override
	public T getByKey(K key) {
		return (T) getSession().get(entityClass,key);
	}

	@Override
	public List<T> getAll() {
		Session session = getSession();
		return session.createQuery("from " + entityClass.getName()).list();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
		
	}

}
