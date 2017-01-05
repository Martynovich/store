package com.andersen.service;

import java.util.List;

import com.andersen.domain.Client;
import com.andersen.persistence.ClientDao;

public class ClientService implements CrudServise<Client> {
	
	private ClientDao clientDao;
	
	public ClientService(){
		this.clientDao = new ClientDao();
	}

	public void create(Client entity) {
		clientDao.persist(entity);
	}

	public Client findById(int id) {
		Client client = (Client)clientDao.findById(id);
        return client;
	}

	public List<Client> findAll() {
		List<Client> clients = (List<Client>)clientDao.findAll();
        return clients;
	}

	public void update(Client entity) {
		clientDao.update(entity);
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
	
	/*
	 
	public void update(Client entity) {
		clientDao.update(entity);
	}

	public void delete(Client entity) {
		clientDao.delete(entity);
	}

	public List<Client> findAll() {
		List<Client> clients = (List<Client>)clientDao.findAll();
        return clients;
	}

	public void deleteAll() {
		List<Client> entityList = findAll();
        for (Client entity : entityList) {
            delete(entity);
        }
	}*/
	
	
	
}
