package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.andersen.App;

public class StoreUtil {
	
	private static Logger logger = Logger.getLogger(StoreUtil.class);
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static String userInput;
	
	static void contOrExit(){
		logger.info("For continue enter - cont, for exit enter - exit.");
		try {
			userInput = reader.readLine();
		} catch (IOException e) {
			logger.error(e);
		}
		while(!userInput.equals(App.EXIT) && !userInput.equals(App.CONTINUE)){
			logger.info("Incorrect input. For continue enter - cont");
			logger.info("For exit enter - exit.");
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		isContinue(userInput);
		isExit(userInput);
	}
	
	static void isExit(String userInput){
		if(userInput.equals(App.EXIT)){
			System.exit(0);
		}
	}
	
	static void isContinue(String userInput){
		if(userInput.equals(App.CONTINUE)){
			App.startApp();
		}
	}
}
