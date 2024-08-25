package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Seat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeatDTO {
    private String seatId;

    public SeatDTO(Seat seat) {
        this.seatId = seat.getSeatId();
    }
}