package com.example.demovisma.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demovisma.CustomersList;
import com.example.demovisma.Order;
import com.example.demovisma.ProductsList;
import com.example.demovisma.model.DBCustomer;
import com.example.demovisma.model.DBProduct;
import com.example.demovisma.repo.DaoProduct;

@Controller
public class MainController {
	@Value( "${server.port}" )
	private int port = 8080;

	@Autowired
	private RestTemplate rt;
	
	@Autowired
	private DaoProduct daoProduct;
		
	@PostMapping("/total")
	public String calculateTotal(@ModelAttribute FormObj cart, Model model) {
		String cURL = "http://localhost:" + String.valueOf(port) + "/customer/" + cart.getCustomer();
		DBCustomer c = rt.getForObject(cURL, DBCustomer.class);
		Optional<DBCustomer> customerData = Optional.ofNullable(c);
		if(!customerData.isPresent()) {
			model.addAttribute("error", "Customer not found !");
	        return "result";
		}
		
		List<String> prodIds = cart.getProducts();
		List<String> qtys = cart.getQty();
		
		if(prodIds.size() != qtys.size()) {
			model.addAttribute("error", "Some products have NULL quantities !");
	        return "result";
		}
		
		Map<Long, Integer> prod_qty_pairs =
			IntStream.range(0, prodIds.size())
				.boxed()
			    .collect(Collectors.toMap(i -> Long.valueOf(prodIds.get(i)), i -> Integer.valueOf(qtys.get(i))));

		List<DBProduct> dbProd = (List<DBProduct>) daoProduct.findAllById(prod_qty_pairs.keySet());
		Order o = new Order(c);	//customer order
		for(int i = 0; i < dbProd.size(); i++) {
			//add product and quantity into the cart
			o.add(dbProd.get(i), prod_qty_pairs.get(dbProd.get(i).getId()));
		}
		//and finally get the total price
		float total = o.calculateTotal();
		
		model.addAttribute("success", "Total price: " + String.format("%.2f", total));
        return "result";
	}

	@GetMapping("/shop")
	public String getMain(
			@RequestParam(required=false, 
						defaultValue="Java", 
						name="name") String name, Model model) {

		CustomersList customers = null;
		List<DBCustomer> custObj = null;
		try {
			String cURL = "http://localhost:" + String.valueOf(port) + "/customer/";
			customers = 
				rt.getForObject(cURL, CustomersList.class);
			custObj = customers.getCustomers();
		}
		catch(Exception e) {
			customers = new CustomersList();
		}
		
		ProductsList products = null;
		List<DBProduct> prodObj = null;
		try {
			String pURL = "http://localhost:" + String.valueOf(port) + "/product/";
			products = 
				rt.getForObject(pURL, ProductsList.class);
			prodObj = products.getProducts();
		}
		catch(Exception e) {
			products = new ProductsList();
		}
		
		model.addAttribute("customers", custObj);
		model.addAttribute("products", prodObj);

		return "index";
	}
}
