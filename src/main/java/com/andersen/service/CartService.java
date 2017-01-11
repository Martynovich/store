package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andersen.domain.Cart;
import com.andersen.domain.Client;
import com.andersen.domain.Product;
import com.andersen.persistence.CartDao;

public class CartService implements CrudServise {
	
	private static final Logger logger = Logger.getLogger(CartService.class);
	
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
		logger.info("Start creating cart.");
		Client client = clientService.clientIdInput();
		List<Product> products = new ArrayList<Product>();
		Cart cart = new Cart();
		cart.setClient(client);
		try {
			while (true) {
				logger.info("Add product? Enter yes/no");
				logger.info("For exit enter - exit.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if (userInput.equals("no")) break;
				if (!userInput.equals("yes") && !userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
				}
				products.add(productService.productIdInput());
				logger.info("Product added.");
			}
		} catch(Exception e){
			logger.error(e);
		}
		cart.setProducts(products);
		cartDao.persist(cart);
		logger.info("Cart is added");
		StoreUtil.contOrExit();
	}

	public void findById() {
		logger.info("Start finding by id cart.");
		Cart cart = cartIdInput();
		logger.info("Cart id - " + cart.geId() +" client id - " + cart.getClient().getId() + 
				" order date - " + cart.getDateOfCreation());
		List<Product> products = cart.getProducts();
		logger.info("Products in cart.");
		for(Product product : products){
			logger.info("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
					", product price - " + product.getProdutPrice());
		}
		StoreUtil.contOrExit();
	}

	public void findAll() {
		logger.info("Start finding all carts.");
		List<Cart> carts = cartDao.findAll();
		if(carts == null){
			logger.info("No carts.");
		}
		for(Cart cart : carts){
			logger.info("Cart id - " + cart.geId() +" client id - " + cart.getClient().getId() + 
					" order date - " + cart.getDateOfCreation());
			List<Product> products = cart.getProducts();
			logger.info("Products in cart.");
			if(products.isEmpty()){
				logger.info("Cart is empty.");
			}
			for(Product product : products){
				logger.info("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
						", product price - " + product.getProdutPrice());
			}
			logger.info("\n");
		}
		StoreUtil.contOrExit();
	}

	public void update() {
		logger.info("Start updating cart.");
		Cart cart = cartIdInput();
		Client newClient;
		try{
			while(true){
				logger.info("Change client? yes/no.");
				logger.info("For exit enter - exit");
				
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
					logger.info("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					newClient = clientService.clientIdInput();
					cart.setClient(newClient);
					break;
				}
				if(userInput.equals("no")){
					break;
				}
			}
			while(true){
				logger.info("Change products? yes/no.");
				logger.info("For exit enter - exit");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
					logger.info("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					cart = productsEditor(cart);
					continue;
				}
				if(userInput.equals("no")){
					break;
				}
			}
		}catch(Exception e){
			logger.error(e);
		}
		cartDao.update(cart);
		logger.info("Cart updated.");
	}

	public void deleteById() {
		logger.info("Start deleting by id cart.");
		Cart cart = cartIdInput();
		cartDao.delete(cart);
		logger.info("Cart deleted.");
		StoreUtil.contOrExit();
	}

	public void deleteAll() {
		logger.info("Start deleting all carts.");
		
			List<Cart> cartList = cartDao.findAll();
        	for (Cart cart : cartList) {
        		try {
        			cartDao.delete(cart);
        		}catch(Exception e){
        			logger.error(e);
        		}	
        	}
        	logger.info("Carts are deleted.");
		StoreUtil.contOrExit();
	}
	
	private Cart cartIdInput(){
		Cart cart = null;
		int id;
		while(true){
			logger.info("Enter cart id.\nFor exit enter - exit.");
			try {
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				id = Integer.parseInt(userInput);
				cart = cartDao.findById(id);
				if(cart == null){
					logger.info("Cart with this id does not exist");
					continue;
				}
			} catch (NumberFormatException e) {
				logger.error("Incorect input. Need to enter number.");
				logger.info("Incorrect input. Please enter number");
				continue;
			} catch (IOException e) {
				logger.error(e);
			}
			break;
		}
		return cart;
	}
	private Cart productsEditor(Cart cart){
		List<Product> products = cart.getProducts();
		try{
			while(true){
				logger.info("Add product? yes/no.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
					logger.info("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					products.add(productService.productIdInput());
					logger.info("Product added.");
					continue;
				}
				if(userInput.equals("no")){
					break;
				}
			}
			while(true){
				logger.info("Remove product? yes/no.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
					logger.info("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					products = removeProduct(products);
					logger.info("Products removed.");
					continue;
				}
				if(userInput.equals("no")){
					break;
				}
			}
		}catch(Exception e){
			logger.error(e);
		}
		cart.setProducts(products);
		return cart;
	}
	
	private List<Product> removeProduct(List<Product> products){
		try{
			for(int i = 0 ; i < products.size() ; i++){
				logger.info("Are you want to delete?");
				logger.info("Product id - " + products.get(i).getId() + ", product login - " + products.get(i).getProductName() + 
						", product price - " + products.get(i).getProdutPrice());
				logger.info("yes/no");
				logger.info("For exit enter - exit");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(userInput.equals("yes")){
					products.remove(i);
				}
				if(userInput.equals("no")){
					continue;
				}
				if(!userInput.equals("yes") && userInput.equals("no")){
					logger.info("Enter correct command: yes/no.");
					logger.info("For exit enter - exit");
				}
			}
		}catch(Exception e){
			logger.error(e);
		}
		return products;
	}
}
