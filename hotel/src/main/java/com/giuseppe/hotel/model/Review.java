package com.giuseppe.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int rating;
	
	@Column
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customers customer;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	Room room;

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Review(int rating, String comment, Customers customer, Room room) {
		super();
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.room = room;
	}



	public Review(Long id, int rating, String comment, Customers customer, Room room) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	
}
