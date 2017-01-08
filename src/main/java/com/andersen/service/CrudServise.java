package com.andersen.service;

import java.io.IOException;
import java.util.List;

public interface CrudServise {
	
	public void create() throws IOException;
	
	public void findById();
	
	public void findAll();
	
	public void update();
	
	public void deleteById();
	
	public void deleteAll();

}
