package com.giuseppe.hotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giuseppe.hotel.repository.RoomRepository;
import com.giuseppe.hotel.model.Room;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	
	private final RoomRepository repo;
	
	public RoomController(RoomRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public List<Room> getAllRoom(){
		return repo.findAll();
	}
	
	@PostMapping
	public Room addRoom(@RequestBody Room room) {
		return repo.save(room);
	}

}
