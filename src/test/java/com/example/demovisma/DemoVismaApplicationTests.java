package com.example.demovisma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demovisma.model.DBCustomer;
import com.example.demovisma.model.DBProduct;

import com.example.demovisma.DiscountCalcOp;
import com.example.demovisma.Order;
import com.example.demovisma.model.DBDiscount;
import com.example.demovisma.model.DBSpecialOfferDiscount;
import com.example.demovisma.model.DBVolumeDiscount;
import com.example.demovisma.repo.DaoCustomer;
import com.example.demovisma.repo.DaoProduct;

@SpringBootTest
class DemoVismaApplicationTests {
	@Autowired
	private DaoProduct productRepo;
	
	@Autowired 
	private DaoCustomer customerRepo;
	/*
	 * A simple test for Customer With Products and some discounts. Checks for total
	 * at the end. Will save all the work in a DB, and in test2() will read from DB
	 */
	@Test
	void test1() {
		DBCustomer c = new DBCustomer("Client Name", 5.0f);	//create a customer
		customerRepo.save(c);	//save the customer to the DB
		Order o = new Order(c);	//customer order
		
		//next I create some products, directly but could be fetched from db in a List
		DBProduct product1 = new DBProduct("Chair", 100.0f); 
		DBProduct product2 = new DBProduct("Table", 600.0f);
		DBProduct product3 = new DBProduct("Coffee Table", 300.0f);
		
		Date now = new Date();

		//create one type of discount
		//this will calculate the new price by replacing whatever the price of a 
		// product is with the new price(getAmount()) 
		DBDiscount sof = new DBSpecialOfferDiscount();
		sof.setAmount(50.0f);
		sof.setCalculation(DiscountCalcOp.REPLACEBY);	//price will be replaced by whatever amount
		sof.setDescription("New special price: " + String.valueOf(sof.getAmount()));
		Date expireAt = new Date(now.getTime() + (1000 * 60 * 60 * 24));	//expire tomorrow
		sof.setStarts_on(now);
		sof.setEnds_on(expireAt);
		
		//and another one
		//this will calculate the discount based on amount in %
		DBDiscount sof2 = new DBSpecialOfferDiscount();
		sof2.setAmount(10.0f);
		sof2.setCalculation(DiscountCalcOp.PERCENT);	//price will be discounted with amount%
		sof2.setDescription("Discounted with " + String.valueOf(sof2.getAmount()) + " %");
		sof2.setStarts_on(now);
		sof2.setEnds_on(expireAt);
		
		//and another based on volume
		DBDiscount vol = new DBVolumeDiscount(2);
		vol.setAmount(10.0f);
		vol.setCalculation(DiscountCalcOp.PERCENT);	//price will be discounted with amount%
		vol.setDescription("Discounted " + String.valueOf(vol.getAmount()) + " % for volume > " + vol.getMinq());
		vol.setStarts_on(now);
		vol.setEnds_on(expireAt);

		//set each product discount(s) and add it to the DB
		product1.getDiscounts().add(sof);
		productRepo.save(product1);
		
		product2.getDiscounts().add(sof2);
		productRepo.save(product2);

		product3.getDiscounts().add(vol);
		productRepo.save(product3);

		//add the products in one Order for the Customer
		o.add(product1);
		o.add(product2);
		o.add(product3, 2);
		
		//should be 50.0f from the first product (was replaced by)
		//and -10% from the second = 600 - 10% = 540.0f;
		//so the total = 540.0f + 50.0f => 590.0f;
		//and another extra 5% from the customer -> 560.5f;
		float total = o.calculateTotal();
		assertEquals(1073.5f, total);
	}
}
