package com.andersen.service;

import java.io.IOException;
import java.util.List;

public interface CrudServise<T> {
	
	public void create() throws IOException;
	
	public T findById(int i);
	
	public List<T> findAll();
	
	public void update(int id);
	
	public void deleteById(int id);
	
	public void deleteAll();

}
