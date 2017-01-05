package com.andersen.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.andersen.domain.Product;

public class ProductDao implements DAO<Product> {
	
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

	public void persist(Product entity) {
		getCurrentSession().save(entity);
		
	}

	public void update(Product entity) {
		getCurrentSession().update(entity);
		
	}

	public Product findById(int id) {
		Product product = (Product)getCurrentSession().get(Product.class, id);
        return product;
	}

	public void delete(Product entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		List<Product> product = (List<Product>)getCurrentSession().createQuery("from Product").list();
        return product;
	}

	public void deleteAll() {
		List<Product> entityList = findAll();
        for (Product entity : entityList) {
            delete(entity);
        }
	}
}
