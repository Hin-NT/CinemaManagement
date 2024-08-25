package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMovieService extends IService<Movie> {
    List<Movie> search(String keyword);

//    List<Movie> findMovieByStatus(int status);
    List<Movie> findMoviesShowing();

    List<Movie> findMoviesComingSoon();

    ResponseEntity<String> add(Movie p, MultipartFile file);

    ResponseEntity<String> update(Movie p, MultipartFile file);

}
