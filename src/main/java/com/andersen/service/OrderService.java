package com.andersen.service;

import java.util.List;

import com.andersen.domain.Order;
import com.andersen.persistence.OrderDao;

public class OrderService implements CrudServise<Order> {
	
	private OrderDao orderDao;
	
	public OrderService(){
		this.orderDao = new OrderDao();
	}

	public void create(Order entity) {
		orderDao.persist(entity);
	}

	public Order findById(int i) {
		Order order = (Order)orderDao.findById(i);
        return order;
	}

	public List<Order> findAll() {
		List<Order> orders = (List<Order>)orderDao.findAll();
        return orders;
	}

	public void update(Order entity) {
		orderDao.update(entity);
	}

	public void deleteById(int id) {
		orderDao.deleteById(id);
	}

	public void deleteAll() {
		List<Order> entityList = findAll();
        for (Order entity : entityList) {
        	orderDao.delete(entity);
        }
	}
	
	

}
