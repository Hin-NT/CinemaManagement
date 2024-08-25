package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:title% AND m.status <> 2")
    List<Movie> findMoviesByTitle(@Param("title") String title);

//    @Query("SELECT m FROM Movie m WHERE m.status = :status")
//    List<Movie> findMoviesByStatus(@Param("status") int status);

    @Query(value = "SELECT * FROM tbl_movie m WHERE CURRENT_TIMESTAMP BETWEEN m.release_date AND m.end_date", nativeQuery = true)
    List<Movie> findMoviesShowing();

    @Query(value = "SELECT * FROM tbl_movie m WHERE CURRENT_TIMESTAMP NOT BETWEEN m.release_date AND m.end_date", nativeQuery = true)
    List<Movie> findMoviesComingSoon();

    @Query(value = "SELECT m.movie_id, m.cast, m.description, m.director, m.duration, m.end_date, m.label_id," +
            "m.poster, m.producer, m.release_date, m.status, m.title, m.trailer " +
            "FROM tbl_movie m WHERE m.description  LIKE %:keyword% OR m.title LIKE %:keyword%", nativeQuery = true)
    List<Movie> search(String keyword);

}
