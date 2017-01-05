package com.andersen.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	@Column
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private int productId;
	
	@Column(unique = true, nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private int produtPrice;
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getProdutPrice() {
		return produtPrice;
	}
	
	public void setProdutPrice(int produtPrice) {
		this.produtPrice = produtPrice;
	}

}
