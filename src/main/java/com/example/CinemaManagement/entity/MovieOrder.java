package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_movie_order")
@NoArgsConstructor
public class MovieOrder {
    @Id
    @Column(name = "movie_order_id")
    private String movieOrderId;

    @Column(name = "price")
    private int price;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public MovieOrder(String movieOrderId, int price, LocalDateTime purchaseDate, Supplier supplier, Movie movie, Account account) {
        this.movieOrderId = movieOrderId;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.supplier = supplier;
        this.movie = movie;
        this.account = account;
    }

}
