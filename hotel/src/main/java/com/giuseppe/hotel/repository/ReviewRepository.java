package com.giuseppe.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giuseppe.hotel.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

}
