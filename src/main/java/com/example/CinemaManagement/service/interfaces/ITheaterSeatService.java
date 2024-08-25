package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITheaterSeatService extends IService<TheaterSeat> {
    List<TheaterSeat> findSeatsByTheater(int threadID);

    List<TheaterSeat> findSeatsByType(int threadID, SeatType seatType);

    List<TheaterSeat> findSeatsByStatus(int threadID, SeatStatus statusSeat);

    ResponseEntity<String> addSeatByFile(MultipartFile file);

}
