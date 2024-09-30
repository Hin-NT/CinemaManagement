package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheaterSeatRepository extends JpaRepository<TheaterSeat, Integer> {

    @Query("SELECT ts FROM TheaterSeat ts WHERE ts.theater.theaterId = :theaterId AND ts.seatType = :seatType")
    List<TheaterSeat> findSeatsByType(@Param("theaterId") Integer theaterId, @Param("seatType") SeatType seatType);

    @Query(value = "SELECT * FROM tbl_theater_seat WHERE theater_id = %:theaterId%", nativeQuery = true)
    List<TheaterSeat> findSeatsByTheaterId(int theaterId);

    @Query("SELECT ts.theater.theaterId AS theaterId, ts.theater.theaterName AS theaterName, ts.seatType AS seatType, COUNT(ts) AS seatCount " +
            "FROM TheaterSeat ts " +
            "GROUP BY ts.theater.theaterId, ts.theater.theaterName, ts.seatType")
    List<Object[]> countSeatsByTypeAndTheater();

}
