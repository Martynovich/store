package com.andersen.persistence;

import java.util.List;

public interface DAO<T> {
	
	public void persist(T entity);
	
	public T findById(int id);
	
	public List<T> findAll();
	
	public void update(T entity);
	
	public void deleteById(int id);
	
	public void delete(T entity);

	public void deleteAll();


}
