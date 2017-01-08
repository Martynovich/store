package com.andersen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.andersen.service.ClientService;
import com.andersen.service.CrudServise;
import com.andersen.service.CartService;
import com.andersen.service.ProductService;

public class StoreApp {
	
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
		System.out.print("Hello."); 
		tableSelecting();
		currentSelecting = "action";
		commandSelecting();
	}
	
	private static void tableSelecting(){
		System.out.println("Please select the table.");
		System.out.println("Enter the table number for select.");
		System.out.println("1 - Client");
		System.out.println("2 - Product.");
		System.out.println("3 - Cart.");
		try {
			rawNumber = inputHandler(new String[]{"1", "2", "3"});
		} catch (IOException e) {
			e.printStackTrace();
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
		System.out.println("Please select an action.");
		System.out.println("Enter the action number for select.");
		System.out.printf("1 - Create new %s.\n", tableName);
		System.out.printf("2 - Find %s by id.\n", tableName);
		System.out.printf("3 - Find all %ss.\n", tableName);
		System.out.printf("4 - Update %s.\n", tableName);
		System.out.printf("5 - Delete %s by id.\n", tableName);
		System.out.printf("6 - Delete all %ss.\n", tableName);
		try {
			rawNumber = inputHandler(new String[]{"1", "2", "3", "4", "5", "6"});
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch(rawNumber){
		case 1: try {
				tableServise.create();
			} catch (IOException e) {
				e.printStackTrace();
			}
				break;
		case 2: tableServise.findById();
				break;
		case 3: tableServise = new CartService();
				tableName = "cart";
				break;
		case 4: tableServise = new CartService();
				tableName = "cart";
				break;
		case 5: tableServise = new CartService();
				tableName = "cart";
				break;
		case 6: tableServise = new CartService();
				tableName = "cart";
				break;
		}
	}
	
	private static Integer inputHandler(String[] raws) throws IOException{;
		ArrayList<String> rawsArray = new ArrayList<String>(Arrays.asList(raws));
		userInput = reader.readLine();
		while(!rawsArray.contains(userInput)){
			System.out.printf("Incorrect input. Please select the %s number.\n", currentSelecting);
			System.out.println("For exit enter - exit.");
			userInput = reader.readLine();
			if(userInput.equals(EXIT)){
				System.exit(0);
			}
		}
		return Integer.parseInt(userInput);
	}

}
