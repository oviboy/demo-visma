package com.example.demovisma;

import com.example.demovisma.model.DBProduct;

public class BaseProduct {
	protected DBProduct p;
	
	public BaseProduct() {
		
	}
	
	public void setDBProduct(DBProduct p) {
		this.p = p;
	}
	
	public BaseProduct(DBProduct p) {
		this.p = p;
	}
	
	public float getPrice() {
		return this.p.getPrice();
	}
	
	public String getName() {
		return this.p.getName();
	}
}
