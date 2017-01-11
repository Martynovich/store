package com.andersen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.andersen.service.ClientService;
import com.andersen.service.CrudServise;
import com.andersen.service.CartService;
import com.andersen.service.ProductService;

public class App {
	
	private final static Logger logger = Logger.getLogger(App.class);
	
	private static CrudServise tableServise;
	private static String userInput;
	private static String tableName;
	private static String currentSelecting = "table";
	private static int rawNumber;
	private static BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
	
	public static final String EXIT = "exit";
	public static final String CONTINUE = "cont";
	
	public static void main(String args[]) throws IOException{
		startApp();
	}
	
	public static void startApp(){
		logger.info("Hello.");
		tableSelecting();
		currentSelecting = "action";
		commandSelecting();
	}
	
	private static void tableSelecting(){
		logger.info("Please select the table.");
		logger.info("Enter the table number for select.");
		logger.info("1 - Client");
		logger.info("2 - Product.");
		logger.info("3 - Cart.");
		try {
			rawNumber = inputHandler(new String[]{"1", "2", "3"});
		} catch (IOException e) {
			logger.error(e);
		}
		switch(rawNumber){
			case 1: tableServise = new ClientService();
					tableName = "client";
					break;
			case 2: tableServise = new ProductService();
					tableName = "product";
					break;
			case 3: tableServise = new CartService();
					tableName = "cart";
					break;
		}
	}
	
	private static void commandSelecting(){
		logger.info("Please select an action.");
		logger.info("Enter the action number for select.");
		logger.info("1 - Create new " + tableName + ".");
		logger.info("2 - Find " + tableName + " by id.");
		logger.info("3 - Find all " + tableName + "s.");
		logger.info("4 - Update " + tableName + ".");
		logger.info("5 - Delete " + tableName + " by id.");
		logger.info("6 - Delete all " + tableName + "s.");
		try {
			rawNumber = inputHandler(new String[]{"1", "2", "3", "4", "5", "6"});
		} catch (IOException e) {
			logger.error(e);
		}
		switch(rawNumber){
		case 1: tableServise.create();
				break;
		case 2: tableServise.findById();
				break;
		case 3: tableServise.findAll();
				break;
		case 4: tableServise.update();
				break;
		case 5: tableServise.deleteById();
				break;
		case 6: tableServise.deleteAll();
				break;
		}
	}
	
	private static Integer inputHandler(String[] raws) throws IOException{;
		ArrayList<String> rawsArray = new ArrayList<String>(Arrays.asList(raws));
		userInput = reader.readLine();
		while(!rawsArray.contains(userInput)){
			logger.info("Incorrect input. Please select the " + currentSelecting + " number.");
			logger.info("For exit enter - exit.");
			userInput = reader.readLine();
			if(userInput.equals(EXIT)){
				System.exit(0);
			}
		}
		return Integer.parseInt(userInput);
	}
}
