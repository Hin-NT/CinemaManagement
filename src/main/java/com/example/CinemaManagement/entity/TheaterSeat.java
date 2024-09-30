package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_theater_seat")
@Getter
@Setter
@NoArgsConstructor
public class TheaterSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "seat_type")
    private SeatType seatType;

    @Column(name = "price")
    private int price;

    public TheaterSeat(Seat seat, Theater theater, SeatType seatType, int price) {
        this.seat = seat;
        this.theater = theater;
        this.seatType = seatType;
        this.price = price;
    }

}