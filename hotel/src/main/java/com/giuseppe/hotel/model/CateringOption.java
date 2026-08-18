package com.giuseppe.hotel.model;

public enum CateringOption {
	NONE(0.0),
	BREAKFAST(15.0),
	HALF_BOARD(30.0),
	FULL_BOARD(50.0);
	
	private final double pricePerDay;
	
	CateringOption(double pricePerDay){
		this.pricePerDay = pricePerDay;
	}
	
	public double getPricePerDay() {
		return pricePerDay;
	}

}
