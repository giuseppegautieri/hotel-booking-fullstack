package com.giuseppe.hotel.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.giuseppe.hotel.model.Customers;
import com.giuseppe.hotel.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final CustomerRepository repo;
	
	public CustomUserDetailsService(CustomerRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		Customers customer = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));
		
		return User.builder().username(customer.getEmail()).password(customer.getPassword()).roles(customer.getRole().name()).build();
	}

}
