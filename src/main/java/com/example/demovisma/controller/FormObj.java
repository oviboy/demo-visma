package com.example.demovisma.controller;

import java.util.List;

//used to pass all the data from the cart
public class FormObj {
	private List<String> products;
	private List<String> qty;
	private Integer customer;
	
	public FormObj() {
	}

	public List<String> getQty() {
		return qty;
	}

	public void setQty(List<String> qty) {
		this.qty = qty;
	}
	
	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
}
