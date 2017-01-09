package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.andersen.StoreApp;

public class StoreUtil {
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static String userInput;
	
	static void contOrExit(){
		System.out.println("For continue enter - cont, for exit enter - exit.");
		try {
			userInput = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!userInput.equals(StoreApp.EXIT) && !userInput.equals(StoreApp.CONTINUE)){
			System.out.printf("Incorrect input. For continue enter- cont");
			System.out.println("For exit enter - exit.");
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		isContinue(userInput);
		isExit(userInput);
	}
	
	static void isExit(String userInput){
		if(userInput.equals(StoreApp.EXIT)){
			System.exit(0);
		}
	}
	
	static void isContinue(String userInput){
		if(userInput.equals(StoreApp.CONTINUE)){
			StoreApp.startApp();
		}
	}
}
