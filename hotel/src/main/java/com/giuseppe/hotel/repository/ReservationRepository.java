package com.giuseppe.hotel.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.giuseppe.hotel.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
	@Query("SELECT COUNT(r) > 0 FROM Reservation r " + 
			"WHERE r.room.id = :roomId " + 
			"AND :checkIn < r.checkOutDate " + 
			"AND :checkOut > r.checkInDate")
	boolean isRoomOccupied(@Param("roomId") Long roomId, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);
	
	
	@Query("SELECT COUNT(r) > 0 FROM Reservation r " +
			"WHERE r.customer.id = :customerId " +
			"AND r.room.id = :roomId " +
			"AND r.checkOutDate < :today")
	boolean hasCompletedStay(@Param("customerId") Long customerId,
							 @Param("roomId") Long roomId,
							 @Param("today") LocalDate today);
	
	

}
