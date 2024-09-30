package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_showtime_seat")
@Getter
@Setter
@NoArgsConstructor
public class ShowTimeSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_seat_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private TheaterSeat theaterSeat;

    @Column(name = "seat_status")
    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;

    public ShowTimeSeat(Showtime showtime, TheaterSeat theaterSeat, SeatStatus seatStatus) {
        this.showtime = showtime;
        this.theaterSeat = theaterSeat;
        this.seatStatus = seatStatus;
    }

}
