package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.dto.ShowtimeDTO;
import com.example.CinemaManagement.entity.Showtime;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IShowtimeService extends IService<Showtime> {
    List<Showtime> findShowTimesByMovie(int movieId);

    Map<LocalDate, List<ShowtimeDTO>> getMoviesByShowDates();

    Map<String, List<ShowtimeDTO>> getShowTimesForMovieOnDate(int movieId, LocalDate date);
}
