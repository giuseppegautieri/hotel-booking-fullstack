package com.giuseppe.hotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giuseppe.hotel.dto.ReservationRequest;
import com.giuseppe.hotel.model.Reservation;
import com.giuseppe.hotel.model.ReservationStatus;
import com.giuseppe.hotel.repository.ReservationRepository;
import com.giuseppe.hotel.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	
	private final ReservationRepository reservationRepository;
	
	private final ReservationService reservationService;
	
	
	public ReservationController(ReservationRepository reservationRepository, ReservationService reservationService) {
		this.reservationRepository = reservationRepository;
		this.reservationService = reservationService;
	}
	
	@GetMapping
	List<Reservation> getAllReservation(){
		return reservationRepository.findAll();
	}
	
	@PostMapping
	public Reservation addReservation(@RequestBody ReservationRequest request) {
		return reservationService.createReservation(request);
	}

	@PutMapping("/{id}/cancel")
	public Reservation cancelReservation(@PathVariable Long id) {
		return reservationService.cancelReservation(id);
	}
}
