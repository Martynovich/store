package com.andersen.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.andersen.domain.Client;

import org.hibernate.SessionFactory;

public class ClientDao implements DAO<Client> {
	
	private Session currentSession;
	
	private Transaction currentTransaction;
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(Client.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
	
	public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
	
	public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

	public void persist(Client entity) {//////////////////////////////////////
		openCurrentSessionwithTransaction().save(entity);
		closeCurrentSessionwithTransaction();
		
	}
	
	public Client findById(int id) {
		Client client = (Client)openCurrentSessionwithTransaction().get(Client.class, id);
		closeCurrentSessionwithTransaction();
        return client;
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> findAll() {//////////////////////////////////
		List<Client> client = (List<Client>)openCurrentSessionwithTransaction().createCriteria(Client.class).list();
		closeCurrentSessionwithTransaction();
        return client;
	}

	public void update(Client entity) {
		openCurrentSessionwithTransaction().update(entity);
		closeCurrentSessionwithTransaction();
	}

	

	public void delete(Client entity) {
		openCurrentSessionwithTransaction().delete(entity);
		closeCurrentSessionwithTransaction();
	}
	
	public void deleteById(int id) {
		openCurrentSessionwithTransaction().delete(this.findById(id));
		closeCurrentSessionwithTransaction();
	}

	public void deleteAll() {
		List<Client> entityList = findAll();
        for (Client entity : entityList) {
            delete(entity);
        }
	}
	
}
