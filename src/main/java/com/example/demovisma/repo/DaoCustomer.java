package com.example.demovisma.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demovisma.model.DBCustomer;

@Repository
public interface DaoCustomer extends JpaRepository<DBCustomer, Long> {
	  @Override
	  List<DBCustomer> findAll();
}
