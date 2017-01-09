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
		List<Cart> carts = cartDao.findAll();
		if(carts == null){
			System.out.println("No carts.");
		}
		for(Cart cart : carts){
			System.out.println("Cart id - " + cart.geId() +" client id - " + cart.getClient().getId() + 
					" order date - " + cart.getDateOfCreation());
			List<Product> products = cart.getProducts();
			System.out.println("Products in cart.");
			if(products.isEmpty()){
				System.out.println("Cart is empty.");
			}
			for(Product product : products){
				System.out.println("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
						", product price - " + product.getProdutPrice());
			}
			System.out.println("\n");
		}
		StoreUtil.contOrExit();
	}

	public void update() {
		Cart cart = cartIdInput();
		Client newClient;
		try{
			while(true){
				System.out.println("Change client? yes/no.");
				System.out.println("For exit enter - exit");
				
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
					System.out.println("For exit enter - exit");
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
				System.out.println("Change products? yes/no.");
				System.out.println("For exit enter - exit");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
					System.out.println("For exit enter - exit");
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
			e.printStackTrace();
		}
		cartDao.update(cart);
		System.out.println("Cart updated.");
	}

	public void deleteById() {
		Cart cart = cartIdInput();
		cartDao.delete(cart);
		System.out.println("Cart deleted.");
		StoreUtil.contOrExit();
	}

	public void deleteAll() {
		try {
			List<Cart> cartList = cartDao.findAll();
        	for (Cart cart : cartList) {
        		cartDao.delete(cart);
        	}
		}catch(Exception e){
			System.out.println("");
		}
		System.out.println("All carts are deleted.");
		StoreUtil.contOrExit();
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
	private Cart productsEditor(Cart cart){
		List<Product> products = cart.getProducts();
		try{
			while(true){
				System.out.println("Add product? yes/no.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
					System.out.println("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					products.add(productService.productIdInput());
					System.out.println("Product added.");
					continue;
				}
				if(userInput.equals("no")){
					break;
				}
			}
			while(true){
				System.out.println("Remove product? yes/no.");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(!userInput.equals("yes") && !userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
					System.out.println("For exit enter - exit");
					continue;
				}
				if(userInput.equals("yes")){
					products = removeProduct(products);
					System.out.println("Products removed.");
					continue;
				}
				if(userInput.equals("no")){
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		cart.setProducts(products);
		return cart;
	}
	
	private List<Product> removeProduct(List<Product> products){
		try{
			for(int i = 0 ; i < products.size() ; i++){
				System.out.println("Are you want to delete?");
				System.out.println("Product id - " + products.get(i).getId() + ", product login - " + products.get(i).getProductName() + 
						", product price - " + products.get(i).getProdutPrice());
				System.out.println("yes/no");
				System.out.println("For exit enter - exit");
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				if(userInput.equals("yes")){
					products.remove(i);
				}
				if(userInput.equals("no")){
					continue;
				}
				if(!userInput.equals("yes") && userInput.equals("no")){
					System.out.println("Enter correct command: yes/no.");
					System.out.println("For exit enter - exit");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return products;
	}
}
