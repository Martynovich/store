package com.andersen.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Client  implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(generator="client_increment")
	@GenericGenerator(name="client_increment", strategy = "increment")
	private int id;
	
	@Column(unique = true, nullable = false)
	private String login;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="client", cascade = CascadeType.ALL)
	private List<Cart> carts;
	
	public Client(){
	}
	
	public Client(String login){
		this.login = login;
	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String name) {
		this.login = name;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	
	
}
