package com.andersen.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.andersen.domain.Client;
import com.andersen.domain.Order;
import com.andersen.domain.Product;
import com.andersen.persistence.ClientDao;

public class Store {	
	
	public static void main(String args[]){
		/*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String enterStore = new String();
		System.out.println("Welcome to our store!");
		System.out.println("For Enter enter - enter.");
		System.out.println("For Register enter - register.");
		System.out.println("For Exit enter - exit.");
		while(!enterStore.equals("enter") && !enterStore.equals("register") && !enterStore.equals("exit")){
		try {
			enterStore = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!enterStore.equals("enter") && !enterStore.equals("register") && !enterStore.equals("exit")){
			System.out.println("");
		}
		}
		System.out.println(enterStore);*/
		Product patato = new Product();
		patato.setProductName("patato");
		patato.setProdutPrice(2);
		Product tomato = new Product();
		tomato.setProductName("tomato");
		tomato.setProdutPrice(3);
		Product apple = new Product();
		apple.setProductName("apple");
		apple.setProdutPrice(4);
		Product paneapple = new Product();
		paneapple.setProductName("paneapple");
		paneapple.setProdutPrice(5);
		Client client = new Client();
		client.setLogin("Igor");
		Order order = new Order();
		ArrayList<Product> orderArr = new ArrayList<Product>();
		orderArr.add(patato);
		orderArr.add(tomato);
		orderArr.add(apple);
		order.setClient(client);
		order.setProducts(orderArr);
		order.setDateOfCreation(new Date());
		System.out.println("*** Persist - start ***");;
		
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(Client.class);
		configuration.addAnnotatedClass(Product.class);
		configuration.addAnnotatedClass(Order.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        Session session =  sessionFactory.openSession();
        session.beginTransaction();
        //session.save(client);
        //session.save(patato);
        //session.save(tomato);
        //session.save(apple);
        //session.save(paneapple);
        session.save(orderArr);
        session.getTransaction().commit();
        session.close();

		
		
	}

}
