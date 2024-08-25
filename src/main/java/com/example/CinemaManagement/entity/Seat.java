package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.CinemaManagement.enums.SeatStatus;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_seat")
@Getter
@Setter
@NoArgsConstructor
public class Seat {
    @Id
    @Column(name = "seat_id")
    private String seatId;

//    @Enumerated(EnumType.ORDINAL)
//    @Column(name = "seat_type")
//    private SeatType seatType;
//
//    @Enumerated(EnumType.ORDINAL)
//    @Column(name = "seat_status")
//    private SeatStatus seatStatus;
//
//    @ManyToOne
//    @JoinColumn(name ="theater_id")
//    private Theater theater;

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TheaterSeat> theaterSeats;

    public Seat(String seatId) {
        this.seatId = seatId;
    }

//    public String toJson() {
//        String theatersJson = theaterSeats.stream()
//                .map(theaterSeat -> "    {\"theaterId\": \"" + theaterSeat.getTheater().getTheaterId() + "\"}")
//                .collect(Collectors.joining(",\n"));
//
//        return "{\n" +
//                "  \"seatId\": \"" + seatId + "\",\n" +
//                "  \"seatType\": " + seatType + ",\n" +
//                "  \"seatStatus\": " + seatStatus + ",\n" +
//                "  \"theaters\": [\n" + theatersJson + "\n" +
//                "  ]\n" +
//                "}";
//    }
}