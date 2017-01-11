package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.andersen.domain.Product;
import com.andersen.persistence.ProductDao;


public class ProductService implements CrudServise {
	
	private final static Logger logger = Logger.getLogger(ProductService.class);
	
	private ProductDao productDao;
	private BufferedReader reader;
	private String userInput;
	
	public ProductService(){
		productDao = new ProductDao();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void create() {
		logger.info("Start creating object.");
		logger.info("Enter product's name.");
		logger.info("For exit enter - exit.");
		Product product;
		String productName = new String();
		int productPrice;
		try{
			productName = reader.readLine();
			StoreUtil.isExit(productName);
			logger.info("Enter product's price.");
			userInput = reader.readLine();
			StoreUtil.isExit(userInput);
			productPrice = Integer.parseInt(userInput);
			product = new Product(productName, productPrice);
			productDao.persist(product);
		}catch(NumberFormatException e){
			logger.error("Incorect input. Need to enter number.");
			logger.info("Incorrect product's price. Please enter number. Try again");
			create();
		}catch(Exception e){
			logger.error(e);
			logger.info("This product's name is already exist. Try again");
			create();
		}
		logger.info("Product is added.");
		StoreUtil.contOrExit();
	}

	public void findById() {
		logger.info("Start finding by id product.");
		Product product = productIdInput();
		logger.info("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
				", product price - " + product.getProdutPrice());
		StoreUtil.contOrExit();
	}

	public void findAll() {
		logger.info("Start finding all products.");
		List<Product> products = productDao.findAll();
		if(products == null){
			logger.info("No products.");
		}
		for(Product product : products){
			logger.info("Product id - " + product.getId() + ", product login - " + product.getProductName() + 
					", product price - " + product.getProdutPrice());
		}
		StoreUtil.contOrExit();
	}

	public void update() {
		logger.info("Start updating product.");
		Product product = productIdInput();
		String newName;
		int newPrice;
		while(true){
			logger.info("Enter new product's name");
			logger.info("For exit enter - exit.");
			try{
				newName = reader.readLine();
				StoreUtil.isExit(newName);
				product.setProductName(newName);
				logger.info("Enter new price.");
				userInput = reader.readLine();
				newPrice = Integer.parseInt(userInput);
				product.setProdutPrice(newPrice);
				productDao.update(product);
			} catch(NumberFormatException e) {
				logger.error(e);
				logger.info("Incorrect product's price. Please enter number. Try again");
				continue;
			} catch(Exception e){
				logger.error(e);
				logger.info("This product name is already exist. Try again");
				continue;
			}
			break;
		}
		logger.info("Product updated");
		StoreUtil.contOrExit();
	}

	public void deleteById() {
		logger.info("Start deleting by id product.");
		Product product = productIdInput();
		try{
			productDao.delete(product);
		} catch(Exception e){
			logger.info("This product can not be removed. There are orders with these products.");
			StoreUtil.contOrExit();
		}
		logger.info("Product deleted.");
		StoreUtil.contOrExit();
	}

	public void deleteAll() {
		logger.info("Start deleting all products.");
			List<Product> productList = productDao.findAll();
			for (Product product : productList) {
				try{
					productDao.delete(product);
				}catch(Exception e){
					logger.error("Can't remove product.");
					System.out.println("Product id - " + product.getId() +" can not be removed. There are orders with these products.");
					continue;
				}
			}
			logger.info("Products are deleted.");
		StoreUtil.contOrExit();
	}
	
	Product productIdInput(){
		Product product = null;
		int id;
		while(true){
			logger.info("Enter product id.\nFor exit enter - exit.");
			try {
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				id = Integer.parseInt(userInput);
				product = productDao.findById(id);
				if(product == null){
					logger.info("Product with this id does not exist");
					continue;
				}
			} catch (NumberFormatException e) {
				logger.error(e);
				logger.info("Incorrect input. Please enter number");
				continue;
			} catch (IOException e) {
				logger.error(e);
			}
			break;
		}
		return product;
	}
}
