package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.andersen.domain.Cart;
import com.andersen.domain.Client;
import com.andersen.domain.Product;
import com.andersen.persistence.CartDao;

public class CartService implements CrudServise {
	
	private CartDao cartDao;
	private BufferedReader reader;
	private String userInput;
	private ClientService clientService;
	private ProductService productService;
	
	public CartService(){
		cartDao = new CartDao();
		reader = new BufferedReader(new InputStreamReader(System.in));
		clientService = new ClientService();
		productService = new ProductService();
	}

	public void create() {
		Client client = clientService.clientIdInput();
		List<Product> products = new ArrayList<Product>();
		Cart cart = new Cart();
		cart.setClient(client);
		try {
			while (true) {
				System.out.println("Add product? Enter yes/no");
				System.out.println("For exit enter - exit.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if (userInput.equals("no")) break;
				if (!userInput.equals("yes") && !userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
				}
				products.add(productService.productIdInput());
				System.out.println("Product added.");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		cart.setProducts(products);
		cartDao.persist(cart);
		System.out.println("Cart is added");
		StoreUtil.contOrExit();
	}

	public void findById() {
		Cart cart = cartIdInput();
		System.out.println("Cart id - " + cart.geId() +" client id - " + cart.getClient().getId() + 
				" order date - " + cart.getDateOfCreation());
		List<Product> products = cart.getProducts();
		System.out.println("Products in cart.");
		for(Product product : products){
			System.out.println("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
					", product price - " + product.getProdutPrice());
		}
		StoreUtil.contOrExit();
	}

	public void findAll() {
		List<Cart> cart = (List<Cart>)cartDao.findAll();
	}

	public void update() {
		//cartDao.update(entity);
	}

	public void deleteById() {
		//cartDao.deleteById(id);
	}

	public void deleteAll() {
		List<Cart> entityList = cartDao.findAll();
        for (Cart entity : entityList) {
        	cartDao.delete(entity);
        }
	}
	
	private Cart cartIdInput(){
		Cart cart = null;
		int id;
		while(true){
			System.out.println("Enter cart id.\nFor exit enter - exit.");
			try {
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				id = Integer.parseInt(userInput);
				cart = cartDao.findById(id);
				if(cart == null){
					System.out.println("Cart with this id does not exist");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Incorrect input. Please enter number");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		return cart;
	}
}
