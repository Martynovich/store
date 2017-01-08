package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.andersen.StoreApp;
import com.andersen.domain.Client;
import com.andersen.domain.Product;
import com.andersen.persistence.ClientDao;
import com.andersen.persistence.ProductDao;

import javassist.bytecode.ExceptionsAttribute;

public class ProductService implements CrudServise {
	
	private ProductDao productDao;
	private BufferedReader reader;
	private String userInput;
	
	public ProductService(){
		productDao = new ProductDao();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void create() {
		System.out.println("Enter product's name.");
		System.out.println("For exit enter - exit.");
		Product product;
		String productName = new String();
		int productPrice;
		try{
			productName = reader.readLine();
			StoreUtil.isExit(productName);
			System.out.println("Enter product's price.");
			userInput = reader.readLine();
			StoreUtil.isExit(userInput);
			productPrice = Integer.parseInt(userInput);
			product = new Product(productName, productPrice);
			productDao.persist(product);
		}catch(NumberFormatException e){
			System.out.println("Incorrect product's price. Please enter number. Try again");
			create();
		}catch(Exception e){
			System.out.println("This product's name is already exist. Try again");
			create();
		}
		System.out.println("Product is added.");
		StoreUtil.contOrExit();
	}

	public void findById() {
		Product product = productIdInput();
		System.out.println("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
				", product price - " + product.getProdutPrice());
		StoreUtil.contOrExit();
	}

	public void findAll() {
		List<Product> products = productDao.findAll();
		if(products == null){
			System.out.println("No products.");
		}
		for(Product product : products){
			System.out.println("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
					", product price - " + product.getProdutPrice());
		}
		StoreUtil.contOrExit();
	}

	public void update() {
		Product product = productIdInput();
		String newName;
		int newPrice;
		while(true){
			System.out.println("Enter new product's name");
			System.out.println("For exit enter - exit.");
			try{
				newName = reader.readLine();
				StoreUtil.isExit(newName);
				product.setProductName(newName);
				System.out.println("Enter new price.");
				userInput = reader.readLine();
				newPrice = Integer.parseInt(userInput);
				product.setProdutPrice(newPrice);
				productDao.update(product);
			} catch(NumberFormatException e) {
				System.out.println("Incorrect product's price. Please enter number. Try again");
				continue;
			} catch(Exception e){
				System.out.println("This product name is already exist. Try again");
				continue;
			}
			break;
		}
		System.out.println("Product updated");
		StoreUtil.contOrExit();
		
	}

	public void deleteById() {
		Product product = productIdInput();
		try{
			productDao.delete(product);
		} catch(Exception e){
			System.out.println("This product can not be removed. There are orders with these products.");
			StoreUtil.contOrExit();
		}
		System.out.println("Product deleted.");
		StoreUtil.contOrExit();
	}

	public void deleteAll() {
		try{
			List<Product> productList = productDao.findAll();
			for (Product product : productList) {
	    		productDao.delete(product);
	    	}
		} catch(Exception e){
			System.out.println("Some products can not be removed. There are orders with these products.");
			StoreUtil.contOrExit();
		}
		System.out.println("All product are deleted.");
		StoreUtil.contOrExit();
	}
	
	Product productIdInput(){
		Product product = null;
		int id;
		while(true){
			System.out.println("Enter product id.\nFor exit enter - exit.");
			try {
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				id = Integer.parseInt(userInput);
				product = productDao.findById(id);
				if(product == null){
					System.out.println("Product with this id does not exist");
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
		return product;
	}
}
