package com.home.webdev.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, K extends Serializable>{
	public void create(T entity);
	public void update(T entity);
	public T getByKey(K key);
	public List<T> getAll();
	public void delete(T entity);
}
