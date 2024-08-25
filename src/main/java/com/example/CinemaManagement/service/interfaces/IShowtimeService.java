package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.Showtime;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IShowtimeService extends IService<Showtime> {
    List<Showtime> findShowTimesByMovie(int movieId);

}
