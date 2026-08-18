package com.giuseppe.hotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giuseppe.hotel.dto.ReviewRequest;
import com.giuseppe.hotel.model.Review;
import com.giuseppe.hotel.repository.ReviewRepository;
import com.giuseppe.hotel.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	private final ReviewRepository revRepo;
	private final ReviewService reviewService;
	
	public ReviewController(ReviewRepository revRepo, ReviewService reviewService) {
		this.revRepo = revRepo;
		this.reviewService = reviewService;
	}
	
	@GetMapping
	public List<Review> getAllReview(){
		return revRepo.findAll();
	}
	
	@PostMapping
	public Review addReview(@RequestBody ReviewRequest request) {
		return reviewService.createReview(request);
	}

}
