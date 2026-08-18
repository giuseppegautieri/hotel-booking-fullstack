package com.giuseppe.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuseppe.hotel.model.Customers;

public interface CustomerRepository extends JpaRepository<Customers, Long>{

	Optional<Customers> findByEmail(String email);
}
