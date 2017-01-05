package com.andersen.persistence;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.andersen.domain.Order;


public class OrderDao implements DAO<Order> {
	
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

	public void persist(Order entity) {
		getCurrentSession().save(entity);
	}

	public void update(Order entity) {
		getCurrentSession().update(entity);	
	}

	public Order findById(int id) {
		Order order = (Order)getCurrentSession().get(Order.class, id);
        return order;
	}

	public void delete(Order entity) {
		getCurrentSession().delete(entity);	
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		List<Order> order = (List<Order>)getCurrentSession().createQuery("from Order").list();
        return order;
	}

	public void deleteAll() {
		List<Order> entityList = findAll();
        for (Order entity : entityList) {
            delete(entity);
        }
	}
	



}
