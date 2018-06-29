package com.home.webdev.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericDaoImpl<T,K extends Serializable> implements GenericDao<T, K>{
	
	private final Class<T> persistentClass;
	
	public GenericDaoImpl(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
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
		return (T) getSession().get(persistentClass, key);
	}

	@Override
	public List<T> getAll() {
		Session session = getSession();
		return session.createQuery("from " + persistentClass.getName()).list();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
		
	}

}
