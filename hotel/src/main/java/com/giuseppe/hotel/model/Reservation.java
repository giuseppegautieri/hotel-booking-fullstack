package com.giuseppe.hotel.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDate checkInDate;
	@Column
	private LocalDate checkOutDate;
	@Column
	private Double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	Room room;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customers customer;
	
	@Enumerated(EnumType.STRING)
	private ReservationStatus status = ReservationStatus.CONFIRMED;
	
	@Enumerated(EnumType.STRING)
	private CateringOption cateringOption = CateringOption.NONE;
	
	private Double refundAmount = 0.0;
	
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(Long id, LocalDate checkInDate, LocalDate checkOutDate, Double totalPrice) {
		super();
		this.id = id;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
	}
	
	public Reservation(LocalDate checkInDate, LocalDate checkOutDate, Double totalPrice, Customers customer, Room room) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
		this.customer = customer;
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public CateringOption getCateringOption() {
		return cateringOption;
	}

	public void setCateringOption(CateringOption cateringOption) {
		this.cateringOption = cateringOption;
	}
	
	
	
}
