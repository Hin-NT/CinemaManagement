package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TheaterSeatDTO {
    private int id;
    private String seatId;
    private int theaterId;
    private String theaterName;
    private String seatType;
    private String seatStatus;
    private int price;
    private Map<SeatType, Integer> seatCounts;

    public TheaterSeatDTO(TheaterSeat theaterSeat) {
        this.id = theaterSeat.getId();
        this.seatId = theaterSeat.getSeat().getSeatId();
        this.theaterName = theaterSeat.getTheater().getTheaterName();
        this.seatType = String.valueOf(theaterSeat.getSeatType());
        this.price = theaterSeat.getPrice();
    }

    public TheaterSeatDTO(int theaterId, String theaterName, Map<SeatType, Integer> seatCounts) {
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        this.seatCounts = seatCounts;
    }

    public TheaterSeatDTO(TheaterSeat theaterSeat, SeatStatus seatStatus) {
        this.id = theaterSeat.getId();
        this.seatId = theaterSeat.getSeat().getSeatId();
        this.theaterName = theaterSeat.getTheater().getTheaterName();
        this.seatType = String.valueOf(theaterSeat.getSeatType());
        this.price = theaterSeat.getPrice();
        this.seatStatus = seatStatus.toString();
    }
}

