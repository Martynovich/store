package com.andersen.persistence;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.andersen.domain.Client;
import com.andersen.domain.Order;


public class OrderDao implements DAO<Order> {

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

	public void persist(Order entity) {
		openCurrentSessionwithTransaction().save(entity);
		closeCurrentSessionwithTransaction();
		
	}
	
	public Order findById(int id) {
		Order order = (Order)openCurrentSessionwithTransaction().get(Order.class, id);
		closeCurrentSessionwithTransaction();
        return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		List<Order> order = (List<Order>)openCurrentSessionwithTransaction().createQuery("Delete from order");
		closeCurrentSessionwithTransaction();
        return order;
	}

	public void update(Order entity) {
		openCurrentSessionwithTransaction().update(entity);
		closeCurrentSessionwithTransaction();
	}

	

	public void delete(Order entity) {
		openCurrentSessionwithTransaction().delete(entity);
		closeCurrentSessionwithTransaction();
	}
	
	public void deleteById(int id) {
		openCurrentSessionwithTransaction().delete(this.findById(id));
		closeCurrentSessionwithTransaction();
	}

	public void deleteAll() {
		List<Order> entityList = findAll();
        for (Order entity : entityList) {
            delete(entity);
        }
	}
	
}
