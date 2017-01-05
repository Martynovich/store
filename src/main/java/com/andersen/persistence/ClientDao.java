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
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
	
	public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
	
	public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
	
	public void closeCurrentSession() {
        currentSession.close();
    }
public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

public Session getCurrentSession() {
        return currentSession;
    }

	public void persist(Client entity) {
		getCurrentSession().save(entity);
	}

	public void update(Client entity) {
		getCurrentSession().update(entity);
	}

	public Client findById(int id) {
		Client client = (Client)getCurrentSession().get(Client.class, id);
        return client;
	}

	public void delete(Client entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Client> findAll() {
		List<Client> books = (List<Client>)getCurrentSession().createQuery("from Client").list();
        return books;
	}

	public void deleteAll() {
		List<Client> entityList = findAll();
        for (Client entity : entityList) {
            delete(entity);
        }
	}
	
	
	
	
	/*
	
	public void closeCurrentSession() {
		        currentSession.close();
		    }
	public void closeCurrentSessionwithTransaction() {
		        currentTransaction.commit();
		        currentSession.close();
		    }
	
	public Session getCurrentSession() {
		        return currentSession;
		    }
	public void setCurrentSession(Session currentSession) {
		        this.currentSession = currentSession;
		    }
		    public Transaction getCurrentTransaction() {
		        return currentTransaction;
		    }
		    public void setCurrentTransaction(Transaction currentTransaction) {
		        this.currentTransaction = currentTransaction;
		    }
		    public void delete(Book entity) {
		        getCurrentSession().delete(entity);
		    }
		    @SuppressWarnings("unchecked")
		    public List<Book> findAll() {
		        List<Book> books = (List<Book>) getCurrentSession().createQuery("from Book").list();
		        return books;
		    }
		    public void deleteAll() {
		        List<Book> entityList = findAll();
		        for (Book entity : entityList) {
		            delete(entity);
		        }
		    }
		}*/


}
