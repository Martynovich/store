package com.andersen.service;

import java.util.List;

public interface CrudServise<T> {
	
	public void create(T entity);
	
	public T findById(int i);
	
	public List<T> findAll();
	
	public void update(T t);
	
	public void deleteById(int id);
	
	public void deleteAll();

}
