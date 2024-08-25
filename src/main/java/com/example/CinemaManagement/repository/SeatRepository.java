package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

//    @Query("SELECT ts.seat FROM TheaterSeat ts WHERE ts.theater.theaterId = :theaterId AND ts.seat.seatType = :seatType")
//    List<Seat> findSeatsByType(@Param("theaterId") Integer theaterId, @Param("seatType") SeatType seatType);
//
//    @Query("SELECT ts.seat FROM TheaterSeat ts WHERE ts.theater.theaterId = :theaterId AND ts.seat.seatStatus = :statusSeat")
//    List<Seat> findSeatsByStatus(@Param("theaterId") Integer theaterId, @Param("statusSeat") SeatStatus statusSeat);
//
//    @Query("SELECT ts.seat FROM TheaterSeat ts WHERE ts.theater.theaterId = :theaterId")
//    List<Seat> findSeatsByTheaterId(@Param("theaterId") Integer theaterId);
}