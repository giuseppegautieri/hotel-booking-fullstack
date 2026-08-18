package com.giuseppe.hotel.model;

import jakarta.persistence.*;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String numberRoom;
	@Column
	private String type;
	@Column
	private Double priceForNight;
	
	@Version
	private Integer version;
	
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(Long id, String numberRoom, String type, Double priceForNight) {
		super();
		this.id = id;
		this.numberRoom = numberRoom;
		this.type = type;
		this.priceForNight = priceForNight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumberRoom() {
		return numberRoom;
	}

	public void setNumberRoom(String numberRoom) {
		this.numberRoom = numberRoom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPriceForNight() {
		return priceForNight;
	}

	public void setPriceForNight(Double priceForNight) {
		this.priceForNight = priceForNight;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
