package com.giuseppe.hotel.model;

import jakarta.persistence.*;

@Entity
public class Customers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String surname;
	@Column(unique = true, nullable = false)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public Customers() {
		super();
	}

	public Customers(Long id, String name, String surname, String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
