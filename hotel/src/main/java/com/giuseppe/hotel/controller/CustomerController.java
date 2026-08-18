package com.giuseppe.hotel.controller;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giuseppe.hotel.model.Customers;
import com.giuseppe.hotel.model.Role;
import com.giuseppe.hotel.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	private final CustomerRepository repository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public CustomerController(CustomerRepository repository, BCryptPasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public List<Customers> getAllCustomers(){
		return repository.findAll();
	}
	
	@PostMapping
	public Customers addCustomer(@RequestBody Customers customer) {
		
		String passwordCriptata = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(passwordCriptata);
		customer.setRole(Role.CUSTOMER);
		return repository.save(customer);
	}
	

}
