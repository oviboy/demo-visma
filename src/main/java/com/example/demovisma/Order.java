package com.example.demovisma;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import com.example.demovisma.model.DBCustomer;
import com.example.demovisma.model.DBDiscount;
import com.example.demovisma.model.DBProduct;

public class Order {
	//will hold product and quantity
	private DBCustomer c;
    private HashMap<DBProduct, Integer> cartList = null;
	float total;

    public Order(DBCustomer c) {
    	this.c = c;
    	cartList = new HashMap<>();
    	total = 0.0f;
    }
    
    public void add(DBProduct p) {
    	if(cartList != null) {
    		//update quantity if the product exists or insert a new one
    		cartList.put(p, cartList.getOrDefault(p, 0) + 1);
    	}
    }

    public void add(DBProduct p, int quantity) {
    	if(cartList != null) {
    		//update quantity if the product exists or insert a new one
    		cartList.put(p, cartList.getOrDefault(p, 0) + quantity);
    	}
    }

    public void setDBCustomer(DBCustomer c) {
    	this.c = c;
    }
    
    public DBCustomer getDBCustomer() {
    	return this.c;
    }
    
    public float calculateTotal() {
    	//will be passed to the DiscountDecorator constructor for validation purposes
		Optional<Date> NOW = Optional.ofNullable(new Date());
		
    	cartList.forEach((product, quantity) -> {
    		BaseProduct bp = new BaseProduct(product);
    		
    		//each discount will return a new BaseProduct, with the updated price
        	for(DBDiscount dis: product.getDiscounts()) {
    			bp = new DiscountDecorator(bp, quantity, dis, NOW);
    		}
        	this.total += bp.getPrice();
    	});
    	
    	//and finally apply the rebate from the customer contract
    	this.total = c.getDiscount() > 0 ? this.total - (this.total*c.getDiscount())/100: this.total;;
    	
    	return this.total > 0 ? this.total: 0;
    }
}
