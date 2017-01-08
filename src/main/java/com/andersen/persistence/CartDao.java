package com.andersen.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import com.andersen.domain.Client;
import com.andersen.domain.Product;
import com.andersen.domain.Cart;


public class CartDao implements DAO<Cart> {

	private Session currentSession;
	
	private Transaction currentTransaction;
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(Client.class);
		configuration.addAnnotatedClass(Product.class);
		configuration.addAnnotatedClass(Cart.class);
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

	public void persist(Cart entity) {
		openCurrentSessionwithTransaction().save(entity);
		closeCurrentSessionwithTransaction();
		
	}
	
	public Cart findById(int id) {
		Cart cart = (Cart)openCurrentSessionwithTransaction().get(Cart.class, id);
		closeCurrentSessionwithTransaction();
        return cart;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cart> findAll() {
		List<Cart> cart = (List<Cart>)openCurrentSessionwithTransaction().createCriteria(Cart.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		closeCurrentSessionwithTransaction();
        return cart;
	}

	public void update(Cart entity) {
		openCurrentSessionwithTransaction().update(entity);
		closeCurrentSessionwithTransaction();
	}

	

	public void delete(Cart entity) {
		openCurrentSessionwithTransaction().delete(entity);
		closeCurrentSessionwithTransaction();
	}
	
	public void deleteById(int id) {
		openCurrentSessionwithTransaction().delete(this.findById(id));
		closeCurrentSessionwithTransaction();
	}

	public void deleteAll() {
		List<Cart> entityList = findAll();
        for (Cart entity : entityList) {
            delete(entity);
        }
	}
	
}
