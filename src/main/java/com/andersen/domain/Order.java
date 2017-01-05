package com.andersen.domain;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
//import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	@Id
	@Column
	@GeneratedValue(generator="order_increment")
	@GenericGenerator(name="order_increment", strategy = "increment")
	private int orderId;

	@Column
	private int clientId;
	
	//@ManyToMany(targetEntity=com.andersen.domain.Product.class, cascade = CascadeType.ALL)
	//@JoinTable(joinColumns = @JoinColumn(name = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Product> products;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfCreation;
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(Client client) {
		this.clientId = client.getId();
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
