package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.ShowTimeSeat;
import com.example.CinemaManagement.entity.Showtime;
import com.example.CinemaManagement.enums.SeatType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, Map<String, List<TheaterSeatDTO>>> showTimeSeatList;

    public ShowtimeDTO(Showtime showtime, int choose) {
        this.showtimeId = showtime.getShowtimeId();
        this.theater = showtime.getTheater() != null ? new TheaterDTO(showtime.getTheater()) : null;
        this.movie = showtime.getMovie() != null ? new MovieDTO(showtime.getMovie(), 0) : null;
        this.startTime = showtime.getStartTime();
        this.endTime = showtime.getEndTime();


        if(choose == 1) {
            ticketOrderList = showtime.getTicketOrderList().stream().map(ticketOrder -> new TicketOrderDTO(ticketOrder, 0)).collect(Collectors.toList());
        }

        if (choose == 2) {
            showTimeSeatList = new HashMap<>(); // Initialize the map

            // Grouping logic
            for (ShowTimeSeat showTimeSeat : showtime.getShowTimeSeats()) {
                String seatType = String.valueOf(showTimeSeat.getTheaterSeat().getSeatType());
                String row = showTimeSeat.getTheaterSeat().getSeat().getSeatId().substring(0, 1); // Assuming the first character is the row identifier

                showTimeSeatList.putIfAbsent(seatType, new HashMap<>());
                showTimeSeatList.get(seatType).putIfAbsent(row, new ArrayList<>());

                // Convert ShowTimeSeat to TheaterSeatDTO and add to the respective list
                showTimeSeatList.get(seatType).get(row).add(new TheaterSeatDTO(showTimeSeat.getTheaterSeat(), showTimeSeat.getSeatStatus()));
            }
        }
    }

    public ShowtimeDTO(Showtime showtime) {
        this.showtimeId = showtime.getShowtimeId();
        this.theater = showtime.getTheater() != null ? new TheaterDTO(showtime.getTheater()) : null;
        this.startTime = showtime.getStartTime();
        this.endTime = showtime.getEndTime();
    }
}
