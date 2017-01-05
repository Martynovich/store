package com.andersen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.andersen.service.ClientService;
import com.andersen.service.CrudServise;
import com.andersen.service.OrderService;
import com.andersen.service.ProductService;

public class StoreApp {
	
	private static CrudServise tableServise;
	private String userInput;
	private int rawNumber;
	
	public static void main(String args[]) throws IOException{
		startApp();
	}
	
	public static void startApp() throws IOException{
		String userInput;
		int rawNumber;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Hello. Please select the table.");
		System.out.println("Enter the table number for select.");
		System.out.println("1 - Client");
		System.out.println("2 - Product.");
		System.out.println("3 - Order.");
		System.out.println("Enter the table number for select.");
		userInput = reader.readLine();
		while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")){
			System.out.println("Incorrect input. Please select the table number.");
			System.out.println("For exit enter - exit.");
			userInput = reader.readLine();
			if(userInput.equals("exit")){
				System.exit(0);
			}
		}
		rawNumber = Integer.parseInt(userInput);
		switch(rawNumber){
			case 1: tableServise = new ClientService();
			case 2: tableServise = new ProductService();
			case 3: tableServise = new OrderService();
		}
		System.out.println("Please select an action.");
		System.out.println("Enter the action number for select.");
		System.out.println("1 - Client");
		System.out.println("2 - Product.");
		System.out.println("3 - Order.");
		System.out.println("Enter the table number for select.");
	}
	
	private static void 

}
