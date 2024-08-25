package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    @Query("SELECT s FROM Showtime s INNER JOIN s.movie m WHERE m.movieId = :movieID")
    List<Showtime> findAllByMovie(@Param("movieID") int movieID);

    @Query("SELECT s FROM Showtime s WHERE s.movie.movieId = :movieId AND s.startTime >= :startTime AND s.endTime <= :endTime")
    List<Showtime> findShowTimesByMovieIdAndDate(@Param("movieId") int movieId,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime);
}
