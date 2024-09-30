package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.ShowTimeSeat;
import com.example.CinemaManagement.enums.SeatStatus;

public class ShowTimeSeatDTO {

    public TheaterSeatDTO theaterSeat;
    public SeatStatus seatStatus;

    public ShowTimeSeatDTO(ShowTimeSeat showTimeSeat) {
        this.theaterSeat = new TheaterSeatDTO(showTimeSeat.getTheaterSeat());
        this.seatStatus = showTimeSeat.getSeatStatus();

    }
}
