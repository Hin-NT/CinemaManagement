package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Showtime;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ShowtimeDTO {
    private int showtimeId;
    private TheaterDTO theater;
    private MovieDTO movie;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<TicketOrderDTO> ticketOrderList;

    public ShowtimeDTO(Showtime showtime, int choose) {
        this.showtimeId = showtime.getShowtimeId();
        this.theater = showtime.getTheater() != null ? new TheaterDTO(showtime.getTheater()) : null;
        this.movie = showtime.getMovie() != null ? new MovieDTO(showtime.getMovie(), 0) : null;
        this.startTime = showtime.getStartTime();
        this.endTime = showtime.getEndTime();


        if(choose == 1) {
            ticketOrderList = showtime.getTicketOrderList().stream().map(ticketOrder -> new TicketOrderDTO(ticketOrder, 0)).collect(Collectors.toList());
        }
    }

    public ShowtimeDTO(Showtime showtime) {
        this.showtimeId = showtime.getShowtimeId();
        this.theater = showtime.getTheater() != null ? new TheaterDTO(showtime.getTheater()) : null;
        this.startTime = showtime.getStartTime();
        this.endTime = showtime.getEndTime();
    }
}
