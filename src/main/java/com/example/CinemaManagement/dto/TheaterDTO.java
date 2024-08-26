package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Theater;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TheaterDTO {
    private int theaterId;
    private String theaterName;
    private List<SeatDTO> seatDTOList;


    public TheaterDTO(Theater theater) {
        this.theaterId = theater.getTheaterId();
        this.theaterName = theater.getTheaterName();

    }
}

