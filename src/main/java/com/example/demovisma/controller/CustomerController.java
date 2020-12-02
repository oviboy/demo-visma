package com.example.demovisma.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demovisma.CustomersList;
import com.example.demovisma.model.DBCustomer;
import com.example.demovisma.repo.DaoCustomer;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private DaoCustomer customer;
	
	@GetMapping("/{cid}")
	public Optional<DBCustomer> getCustomer(@PathVariable("cid") Long cid) {
		return customer.findById(cid);
	}
	
	@GetMapping("/")
	public CustomersList getCustomers() {
	    /*final List<on.visma.project.Model.DBCustomer> customersList = (new ArrayList<>();

	    Iterable<on.visma.project.Model.DBCustomer> iterable = customer.findAll();
	    iterable.forEach(customersList::add);
	   	return customersList;
	     */
		return new CustomersList(customer.findAll());
	}
}
