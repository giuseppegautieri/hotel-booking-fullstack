package com.giuseppe.hotel.dto;

import java.time.LocalDate;

import com.giuseppe.hotel.model.CateringOption;

public class ReservationRequest {
	
	private Long customerId;
	
	private Long roomId;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	private CateringOption cateringOption;

	public ReservationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationRequest(Long customerId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
		super();
		this.customerId = customerId;
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public CateringOption getCateringOption() {
		return cateringOption;
	}

	public void setCateringOption(CateringOption cateringOption) {
		this.cateringOption = cateringOption;
	}
	
	

}
