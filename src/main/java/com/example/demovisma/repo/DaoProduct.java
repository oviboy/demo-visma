package com.example.demovisma.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demovisma.model.DBProduct;

public interface DaoProduct extends CrudRepository<DBProduct, Long> {
	  @Override
	  List<DBProduct> findAll();
}
