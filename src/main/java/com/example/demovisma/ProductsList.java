package com.example.demovisma;

import java.util.ArrayList;
import java.util.List;

import com.example.demovisma.model.DBProduct;

//wrapper class for products response
public class ProductsList {
    private List<DBProduct> products;

    public ProductsList() {
    	this.products = new ArrayList<>();
    }

    public ProductsList(List<DBProduct> products) {
        this.products = products;
    }

	public List<DBProduct> getProducts() {
		return products;
	}

	public void setProducts(List<DBProduct> products) {
		this.products = products;
	}
}

