package com.andersen.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.andersen.domain.Client;
import com.andersen.domain.Product;

public class ProductDao implements DAO<Product> {

	private Session currentSession;
	
	private Transaction currentTransaction;
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(Product.class);
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

	public void persist(Product entity) {
		openCurrentSessionwithTransaction().save(entity);
		closeCurrentSessionwithTransaction();
		
	}
	
	public Product findById(int id) {
		Product product = (Product)openCurrentSessionwithTransaction().get(Product.class, id);
		closeCurrentSessionwithTransaction();
        return product;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		List<Product> product = (List<Product>)openCurrentSessionwithTransaction().createQuery("Delete from product");
		closeCurrentSessionwithTransaction();
        return product;
	}

	public void update(Product entity) {
		openCurrentSessionwithTransaction().update(entity);
		closeCurrentSessionwithTransaction();
	}

	public void delete(Product entity) {
		openCurrentSessionwithTransaction().delete(entity);
		closeCurrentSessionwithTransaction();
	}
	
	public void deleteById(int id) {
		openCurrentSessionwithTransaction().delete(this.findById(id));
		closeCurrentSessionwithTransaction();
	}

	public void deleteAll() {
		List<Product> entityList = findAll();
        for (Product entity : entityList) {
            delete(entity);
        }
	}
}
