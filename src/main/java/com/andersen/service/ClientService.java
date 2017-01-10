package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.andersen.StoreApp;
import com.andersen.domain.Client;
import com.andersen.persistence.ClientDao;

public class ClientService implements CrudServise {
	
	private final static Logger logger = Logger.getLogger(ClientService.class);
	
	private ClientDao clientDao;
	private BufferedReader reader;
	private String userInput;
	
	public ClientService(){
		clientDao = new ClientDao();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void create() {
		logger.info("Start creating client.");
		System.out.println("Enter clientname.");
		System.out.println("For exit enter - exit.");
		try {
			userInput = reader.readLine();
			StoreUtil.isExit(userInput);
			clientDao.persist(new Client(userInput)); 
		} catch (Exception e) {
			logger.error("Login is already exist.");
				System.out.println("This login is already exist. Try again");
				this.create();
		}
		System.out.println("Client is added.");
		StoreUtil.contOrExit();
	}

	public void findById() {
		logger.info("Start finding by id client.");
		Client client = clientIdInput();
		System.out.println("Client id - " + client.getId() + " client login - " + client.getLogin());
		StoreUtil.contOrExit();
	}

	public void findAll() {
		logger.info("Start finding all clients.");
		List<Client> clients = clientDao.findAll();
		if(clients == null){
			System.out.println("No clients.");
		}
		for(Client client : clients){
			System.out.println("Client id - " + client.getId() + " client login - " + client.getLogin());
		}
		StoreUtil.contOrExit();
	}

	public void update() {
		logger.info("Start updating client.");
		Client client = clientIdInput();
		while(true){
			System.out.println("Enter new login");
			System.out.println("For exit enter - exit.");
			try{
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				client.setLogin(userInput);
				clientDao.update(client);
			} catch(Exception e) {
				logger.error("Login is already exist.");
				System.out.println("This login is already exist. Try again");
				continue;
			}
			break;
		}
		System.out.println("Client updated");
		StoreUtil.contOrExit();
	}

	public void deleteById() {
		logger.info("Start deleting by id client.");
		Client client = clientIdInput();
		clientDao.delete(client);
		System.out.println("Client deleted.");
		StoreUtil.contOrExit();
	}

	public void deleteAll() {
		logger.info("Start deleting all clients.");
			List<Client> clientList = clientDao.findAll();
        	for (Client client : clientList) {
        		try {
        			clientDao.delete(client);
        		}catch(Exception e){
        			logger.error("Can't delete client.");
        		}	
        	}
	}
	
	Client clientIdInput(){
		System.out.println();
		Client client = null;
		int id;
		while(true){
			System.out.println("Enter client id.\nFor exit enter - exit.");
			try {
				userInput = reader.readLine();
				StoreUtil.isExit(userInput);
				id = Integer.parseInt(userInput);
				client = clientDao.findById(id);
				if(client == null){
					System.out.println("Client with this id does not exist");
					continue;
				}
			} catch (NumberFormatException e) {
				logger.error("Incorect input. Need to enter number.");
				System.out.println("Incorrect input. Please enter number");
				continue;
			} catch (IOException e) {
				logger.error(e);
			}
			break;
		}
		return client;
	}
}
