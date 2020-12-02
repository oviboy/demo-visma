package com.example.demovisma;

import java.util.ArrayList;
import java.util.List;

import com.example.demovisma.model.DBCustomer;

//wrapper class for customers response
public class CustomersList {
	public List<DBCustomer> customers;
 
    public CustomersList() {
    	customers = new ArrayList<>();
    }
    
    public CustomersList(List<DBCustomer> customers) {
        this.customers = customers;
    }

	public List<DBCustomer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<DBCustomer> customers) {
		this.customers = customers;
	}
}

