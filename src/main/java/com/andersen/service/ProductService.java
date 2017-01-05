package com.andersen.service;

import java.util.List;

import com.andersen.domain.Product;
import com.andersen.persistence.ProductDao;

public class ProductService implements CrudServise<Product> {
	
	ProductDao productDao;
	
	public ProductService(){
		productDao = new ProductDao();
	}

	public void create(Product entity) {
		productDao.persist(entity);
	}

	public Product findById(int i) {
		Product product = (Product)productDao.findById(i);
        return product;
	}

	public List<Product> findAll() {
		List<Product> product = (List<Product>)productDao.findAll();
        return product;
	}

	public void update(Product entity) {
		productDao.update(entity);
		
	}

	public void deleteById(int id) {
		productDao.deleteById(id);	
	}

	public void deleteAll() {
		List<Product> entityList = findAll();
        for (Product entity : entityList) {
        	productDao.delete(entity);
        }
	}
	
}
