package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    @Query("SELECT s FROM Showtime s INNER JOIN s.movie m WHERE m.movieId = :movieID")
    List<Showtime> findAllByMovie(@Param("movieID") int movieID);

    @Query("SELECT s FROM Showtime s WHERE s.movie.movieId = :movieId AND s.startTime >= :startTime AND s.endTime <= :endTime")
    List<Showtime> findShowTimesByMovieIdAndDate(@Param("movieId") int movieId,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime);

    @Query("SELECT DISTINCT FUNCTION('DATE', s.startTime) FROM Showtime s")
    List<java.sql.Date> findAllUniqueShowDates();

    @Query("SELECT s FROM Showtime s WHERE FUNCTION('DATE', s.startTime) = :date")
    List<Showtime> findShowTimesByDate(@Param("date") LocalDate date);

    @Query("SELECT s FROM Showtime s WHERE s.theater.theaterId = :theaterId AND " +
            "(:startTime BETWEEN s.startTime AND s.endTime OR " +
            ":endTime BETWEEN s.startTime AND s.endTime OR " +
            "s.startTime BETWEEN :startTime AND :endTime)")
    List<Showtime> findConflictingShowTimes(@Param("theaterId") int theaterId,
                                            @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT s FROM Showtime s WHERE s.movie.movieId = :movieId AND FUNCTION('DATE', s.startTime) = :date ORDER BY s.startTime ASC")
    List<Showtime> findShowTimesByMovieIdAndDateSorted(@Param("movieId") int movieId, @Param("date") LocalDate date);
}
