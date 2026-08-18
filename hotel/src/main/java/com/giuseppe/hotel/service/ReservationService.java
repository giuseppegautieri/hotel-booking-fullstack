package com.giuseppe.hotel.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.giuseppe.hotel.dto.ReservationRequest;
import com.giuseppe.hotel.model.Customers;
import com.giuseppe.hotel.model.Reservation;
import com.giuseppe.hotel.model.ReservationStatus;
import com.giuseppe.hotel.model.Room;
import com.giuseppe.hotel.repository.CustomerRepository;
import com.giuseppe.hotel.repository.ReservationRepository;
import com.giuseppe.hotel.repository.RoomRepository;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;

	private final CustomerRepository customerRepository;

	private final RoomRepository roomRepository;
	
	private final EmailService emailService;

	public ReservationService(ReservationRepository reservationRepository, 
			CustomerRepository customerRepository, 
			RoomRepository roomRepository,
			EmailService emailService) {
		this.reservationRepository = reservationRepository;
		this.customerRepository = customerRepository;
		this.roomRepository = roomRepository;
		this.emailService = emailService;
	}

	public Reservation createReservation(ReservationRequest request) {

		Customers customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new RuntimeException("Cliente non trovato"));

		Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new RuntimeException("Stanza non trovata"));

		boolean occupied = reservationRepository.isRoomOccupied(room.getId(), request.getCheckInDate(), request.getCheckOutDate());

		if(occupied) {
			throw new RuntimeException("La stanza è già occupata in queste date!");
		}

		long days = java.time.temporal.ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
		
		double dailyCateringPrice = request.getCateringOption().getPricePerDay();

		Double totalPrice = days * (room.getPriceForNight() + dailyCateringPrice);

		Reservation reservation = new Reservation(request.getCheckInDate(), request.getCheckOutDate(), totalPrice, customer, room);

		roomRepository.save(room);
		
		reservation.setCateringOption(request.getCateringOption());
		
		Reservation savedReservation = reservationRepository.save(reservation);
		
		
		emailService.sendBookingConfirmation(customer.getEmail(), savedReservation.getId(), room.getNumberRoom(), savedReservation.getTotalPrice());
		return savedReservation;
	}
	
	public Reservation cancelReservation(Long id) {
		
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
		
		if(reservation.getStatus() == ReservationStatus.CANCELLED) {
			throw new RuntimeException("Questa prenotazione è già stata cancellata in passato!");
		}
		
		LocalDate oggi = LocalDate.now();
		
		long giorniAlCheckIn = java.time.temporal.ChronoUnit.DAYS.between(oggi, reservation.getCheckInDate());
		
		if(giorniAlCheckIn < 0) {
			throw new RuntimeException("Impossibile cancellare un soggiorno già iniziao o passato");
		}
		
		double prezzoTotale = reservation.getTotalPrice();
		double rimborso = 0.0;
		
		if(giorniAlCheckIn < 7) {
			rimborso = prezzoTotale;
		}else if(giorniAlCheckIn >= 3 && giorniAlCheckIn <= 7) {
			rimborso = prezzoTotale*0.5;
		}else {
			rimborso = 0.0;
		}
		
		reservation.setStatus(ReservationStatus.CANCELLED);
		reservation.setRefundAmount(rimborso);
		
		System.out.println("Cancellazione completata per l'ID: " + id + " Rimborso calcolato: " + rimborso + "€");
		return reservationRepository.save(reservation); 
	}
	
	

}


