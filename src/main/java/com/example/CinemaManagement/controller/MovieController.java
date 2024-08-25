package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.MovieDTO;
import com.example.CinemaManagement.entity.Category;
import com.example.CinemaManagement.entity.Label;
import com.example.CinemaManagement.entity.Movie;
import com.example.CinemaManagement.enums.MovieStatus;
import com.example.CinemaManagement.service.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/public/")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<Movie> movies = movieService.getAll();
        List<MovieDTO> movieDTOList = movies.stream().map(movie -> new MovieDTO(movie, 1)).toList();
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/public/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable int movieId) {
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        return ResponseEntity.ok(new MovieDTO(movieService.getById(movie), 1));
    }

    @GetMapping("/public/filter")
    public ResponseEntity<List<MovieDTO>> getMoviesByStatus(@RequestParam("keyword") int keyword) {
        List<Movie> movies;
        if(keyword == MovieStatus.NOW_SHOWING.ordinal()) {
            movies = movieService.findMoviesShowing();
        } else {
            movies = movieService.findMoviesComingSoon();
        }
        List<MovieDTO> movieDTOList = movies.stream().map(movie -> new MovieDTO(movie, 0)).toList();
        return ResponseEntity.ok(movieDTOList);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("")
    public ResponseEntity<String> createMovie(
            @RequestParam("title") String title,
            @RequestParam("duration") int duration,
            @RequestParam("posterFile") MultipartFile posterFile,
            @RequestParam("trailer") String trailer,
            @RequestParam("description") String description,
            @RequestParam("director") String director,
            @RequestParam("cast") String cast,
            @RequestParam("producer") String producer,
            @RequestParam("releaseDate") LocalDate releaseDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("categories") Set<Category> categories,
            @RequestParam("label") Label label,
            @RequestParam("status") MovieStatus status)
    {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setTrailer(trailer);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setCast(cast);
        movie.setProducer(producer);
        movie.setReleaseDate(releaseDate);
        movie.setEndDate(endDate);
        movie.setCategories(categories);
        movie.setLabel(label);
        movie.setStatus(status);
        return movieService.add(movie, posterFile);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/{movieId}")
    public ResponseEntity<String> updateMovie(@Valid @RequestBody Movie movie) {
        return movieService.update(movie);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable int movieId) {
        Movie createMovie = new Movie();
        createMovie.setMovieId(movieId);
        return movieService.delete(createMovie);
    }

    @GetMapping("/public/search")
    public ResponseEntity<List<MovieDTO>> searchMoviesByTitle(@RequestParam("keyword") String keyword) {
        List<Movie> movies = movieService.search(keyword);
        List<MovieDTO> movieDTOList = movies.stream().map(movie -> new MovieDTO(movie, 1)).toList();
        return ResponseEntity.ok(movieDTOList);
    }

    //    @GetMapping("/public/status/{movieType}")
    //    public ResponseEntity<List<MovieDTO>> getMoviesByStatus(@PathVariable int movieType) {
    //        List<Movie> movies = movieService.findMovieByStatus(movieType);
    //        List<MovieDTO> movieDTOList = movies.stream().map(movie -> new MovieDTO(movie, 0)).toList();
    //        return ResponseEntity.ok(movieDTOList);
    //    }

}
