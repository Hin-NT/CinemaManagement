package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_theater")
@Getter
@Setter
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private int theaterId;

    @Column(name = "theater_name")
    private String theaterName;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TheaterSeat> theaterSeats;

    public Theater(String theaterName) {
        this.theaterName = theaterName;
    }

    public Theater(int theaterId) {
        this.theaterId = theaterId;
    }

}