package com.andersen.service;

import java.util.List;

import com.andersen.domain.Cart;
import com.andersen.persistence.CartDao;

public class CartService implements CrudServise<Cart> {
	
	private CartDao cartDao;
	
	public CartService(){
		this.cartDao = new CartDao();
	}

	public void create() {
		//cartDao.persist(entity);
	}

	public Cart findById(int i) {
		Cart cart = (Cart)cartDao.findById(i);
        return cart;
	}

	public List<Cart> findAll() {
		List<Cart> cart = (List<Cart>)cartDao.findAll();
        return cart;
	}

	public void update(int id) {
		//cartDao.update(entity);
	}

	public void deleteById(int id) {
		cartDao.deleteById(id);
	}

	public void deleteAll() {
		List<Cart> entityList = findAll();
        for (Cart entity : entityList) {
        	cartDao.delete(entity);
        }
	}
	
	

}
