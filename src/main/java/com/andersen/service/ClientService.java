package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.andersen.StoreApp;
import com.andersen.domain.Client;
import com.andersen.persistence.ClientDao;

public class ClientService implements CrudServise<Client> {
	
	private ClientDao clientDao;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private String userInput;
	
	public ClientService(){
		this.clientDao = new ClientDao();
	}

	public void create() throws IOException {
		System.out.println("Enter username.");
		System.out.println("For exit enter - exit.");
		try {
			userInput = reader.readLine();
			isExit();
			clientDao.persist(new Client(userInput)); 
		} catch (Exception e) {
				System.out.println("This login is already exist. Try again");
				this.create();
		}
		System.out.println("Client is added.");
		contOrExit();
	}

	public Client findById(int id) {
		Client client = (Client)clientDao.findById(id);
		return client;
	}

	public List<Client> findAll() {
		List<Client> clients = (List<Client>)clientDao.findAll();
        return clients;
	}

	public void update(int id) {
		//clientDao.update(entity);
	}

	public void deleteById(int id) {
		clientDao.deleteById(id);		
	}

	public void deleteAll() {
		List<Client> entityList = findAll();
        for (Client entity : entityList) {
        	clientDao.delete(entity);
        }
	}
	
	private void contOrExit() throws IOException{
		System.out.println("For continue enter - cont, for exit enter - exit.");
		userInput = reader.readLine();
		while(!userInput.equals(StoreApp.EXIT) && !userInput.equals(StoreApp.CONTINUE)){
			System.out.printf("Incorrect input. For continue enter- cont");
			System.out.println("For exit enter - exit.");
			userInput = reader.readLine();
		}
		isContinue();
		isExit();
	}
	
	private void isExit(){
		if(userInput.equals(StoreApp.EXIT)){
			System.exit(0);
		}
	}
	
	private void isContinue() throws IOException{
		if(userInput.equals(StoreApp.CONTINUE)){
			StoreApp.startApp();
		}
	}
}
