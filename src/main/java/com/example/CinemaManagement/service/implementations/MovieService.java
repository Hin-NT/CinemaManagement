package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Movie;
import com.example.CinemaManagement.repository.MovieRepository;
import com.example.CinemaManagement.service.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MovieService(MovieRepository movieRepository, CloudinaryService cloudinaryService) {
        this.movieRepository = movieRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getById(Movie movie) {
        return movieRepository.findById(movie.getMovieId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(Movie movie) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Movie movie) {
        return null;
    }

    @Override
    public ResponseEntity<String> add(Movie movie, MultipartFile posterFile) {
        try {
            String imageUrl = cloudinaryService.uploadImage(posterFile);
            movie.setPoster(imageUrl);
            movieRepository.save(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed  to add movie due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Movie movie, MultipartFile posterFile) {
        Optional<Movie> movieOptional = movieRepository.findById(movie.getMovieId());
        if (movieOptional.isPresent()) {
            try {
                if(posterFile != null) {
                    String imageUrl = cloudinaryService.uploadImage(posterFile);
                    movie.setPoster(imageUrl);
                }
                movieRepository.save(movie);
                return ResponseEntity.status(HttpStatus.OK).body("Movie updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed  to update movie due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found!");
        }
    }

//    @Override
//    public ResponseEntity<String> update(Movie movie, MultipartFile posterFile) {
//        Optional<Movie> movieOptional = movieRepository.findById(movie.getMovieId());
//        if (movieOptional.isPresent()) {
//            try {
//                Movie movieToUpdate = movieOptional.get();
//                if(posterFile != null) {
//                    String imageUrl = cloudinaryService.uploadImage(posterFile);
//                    movieToUpdate.setPoster(imageUrl);
//                }
//                movieToUpdate.setTitle(movie.getTitle());
//                movieToUpdate.setDuration(movie.getDuration());
//                movieToUpdate.setTrailer(movie.getTrailer());
//                movieToUpdate.setDescription(movie.getDescription());
//                movieToUpdate.setDirector(movie.getDirector());
//                movieToUpdate.setCast(movie.getCast());
//                movieToUpdate.setProducer(movie.getProducer());
//                movieToUpdate.setReleaseDate(movie.getReleaseDate());
//                movieToUpdate.setEndDate(movie.getEndDate());
//                movieToUpdate.setCategories(movie.getCategories());
//                movieToUpdate.setLabel(movie.getLabel());
//                movieToUpdate.setStatus(movie.getStatus());
//                movieRepository.save(movie);
//                return ResponseEntity.status(HttpStatus.OK).body("Movie updated successfully!");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed  to update movie due to: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found!");
//        }
//    }

    @Override
    public ResponseEntity<String> delete(Movie movie) {
        try {
            if (movieRepository.existsById(movie.getMovieId())) {
                movieRepository.deleteById(movie.getMovieId());
                return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found with ID: " + movie.getMovieId());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete movie due to: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> search(String keyword) {
        return movieRepository.search(keyword);
    }

//    @Override
//    public List<Movie> findMovieByStatus(int status) {
//        return movieRepository.findMoviesByStatus(status);
//    }

    @Override
    public List<Movie> findMoviesShowing() {
        return movieRepository.findMoviesShowing();
    }

    @Override
    public List<Movie> findMoviesComingSoon() {
        return movieRepository.findMoviesComingSoon();
    }

}
