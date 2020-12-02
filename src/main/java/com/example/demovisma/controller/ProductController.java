package com.example.demovisma.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demovisma.ProductsList;
import com.example.demovisma.model.DBProduct;
import com.example.demovisma.repo.DaoProduct;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private DaoProduct product;
	
	@GetMapping(path= {"/{cid}"})
	public Optional<DBProduct> getProduct(@PathVariable("pid") Long pid) {
		return product.findById(pid);
	}
	
	@GetMapping(path= {"/"})
	public ProductsList getProducts() {
		return new ProductsList(product.findAll());
	}
	
}
