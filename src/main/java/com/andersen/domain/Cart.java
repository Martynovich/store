package com.andersen.domain;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
//import javax.persistence.JoinTable;
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
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	//@Column
	@ManyToOne(cascade = CascadeType.ALL)
	private Client client;
	
	//@ManyToMany(targetEntity=com.andersen.domain.Product.class, cascade = CascadeType.ALL)
	//@JoinTable(joinColumns = @JoinColumn(name = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Product> products;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfCreation;
	
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
