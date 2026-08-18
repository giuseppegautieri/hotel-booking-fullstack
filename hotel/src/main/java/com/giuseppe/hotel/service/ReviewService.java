package com.giuseppe.hotel.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.giuseppe.hotel.dto.ReviewRequest;
import com.giuseppe.hotel.model.Customers;
import com.giuseppe.hotel.model.Review;
import com.giuseppe.hotel.model.Room;
import com.giuseppe.hotel.repository.CustomerRepository;
import com.giuseppe.hotel.repository.ReservationRepository;
import com.giuseppe.hotel.repository.ReviewRepository;
import com.giuseppe.hotel.repository.RoomRepository;

@Service
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	private final CustomerRepository customerRepository;
	
	private final RoomRepository roomRepository;
	
	private final ReservationRepository reservationRepository;
	
	
	
	public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository,
			RoomRepository roomRepository, ReservationRepository reservationRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.customerRepository = customerRepository;
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
	}



	public Review createReview(ReviewRequest request) {
		
		if(request.getRating() < 1 || request.getRating() > 5) {
			throw new RuntimeException("Il voto deve essere compreso tra 1 e 5");
		}
		
		Customers customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new RuntimeException("Cliente non trovato"));
		
		Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new RuntimeException("Stanza non trovata"));
		
		boolean hasStayed = reservationRepository.hasCompletedStay(customer.getId(), room.getId(), LocalDate.now());
		
		if(!hasStayed) {
			throw new RuntimeException("Non puoi effettuare la recensione della stanza per la quale non hai soggiornato in passato!");
		}
		
		Review review = new Review(request.getRating(), request.getComment(), customer, room);
		return reviewRepository.save(review);
	}

}
