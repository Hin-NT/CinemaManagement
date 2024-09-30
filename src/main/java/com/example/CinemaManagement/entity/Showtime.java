package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_showtime")
@Getter
@Setter
@NoArgsConstructor
public class Showtime {
    @Id
    @Column(name = "showtime_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "theater_id" )
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTimeSeat> showTimeSeats;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<TicketOrder> ticketOrderList;

    public Showtime(LocalDateTime startTime, LocalDateTime endTime, Theater theater, Movie movie) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.theater = theater;
        this.movie = movie;
    }
}
