package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TheaterSeatDTO {
    private int id;
    private String seatId;
    private String theaterName;
    private String seatType;
    private String seatStatus;
    private int price;


    public TheaterSeatDTO(TheaterSeat theaterSeat) {
        this.id = theaterSeat.getId();
        this.seatId = theaterSeat.getSeat().getSeatId();
        this.theaterName = theaterSeat.getTheater().getTheaterName();
        this.seatType = String.valueOf(theaterSeat.getSeatType());
        this.seatStatus = String.valueOf(theaterSeat.getSeatStatus());
        this.price = theaterSeat.getPrice();
    }
}

