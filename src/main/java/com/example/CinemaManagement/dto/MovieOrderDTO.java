package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.MovieOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovieOrderDTO {
    private String movieOrderId;
    private int supplierId;
    private String movieId;
    private int accountId;
    private int price;
    private LocalDate purchaseDate;
    private int totalAmount;
    private SupplierDTO supplier;
    private MovieDTO movie;
    private AccountDTO account;

    public MovieOrderDTO(MovieOrder movieOrder) {
//        if (movieOrder != null) {
//            this.movieOrderId = movieOrder.getMovieOrderId();
//            this.supplierId = movieOrder.getSupplier() != null ? movieOrder.getSupplier().getSupplierId() : 0;
//            this.movieId = movieOrder.getMovie() != null ? String.valueOf(movieOrder.getMovie().getMovieId()) : "";
//            this.accountId = movieOrder.getAccount() != null ? movieOrder.getAccount().getAccountId() : 0;
//            this.price = movieOrder.getPrice();
//            this.purchaseDate = movieOrder.getPurchaseDate() != null ? movieOrder.getPurchaseDate().toLocalDate() : null;
//            this.supplier = movieOrder.getSupplier() != null ? new SupplierDTO(movieOrder.getSupplier()) : null;
//            this.movie = movieOrder.getMovie() != null ? new MovieDTO(movieOrder.getMovie()) : null;
//            this.account = movieOrder.getAccount() != null ? new AccountDTO(movieOrder.getAccount()) : null;
//        }
    }
}
