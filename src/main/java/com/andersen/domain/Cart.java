package com.andersen.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Cart implements Serializable {

	private static final long serialVersionUID = 3L;

	@Id
	@Column
	@GeneratedValue(generator="cart_increment")
	@GenericGenerator(name="cart_increment", strategy = "increment")
	private int id;

	@ManyToOne
    @JoinColumn(name="client_id")
	private Client client;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Product> products = null;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfCreation;
	
	public Cart(){
		dateOfCreation = new Date();
	}
	
	public int geId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

}
