package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.MovieOrder;
import com.example.CinemaManagement.repository.MovieOrderRepository;
import com.example.CinemaManagement.service.interfaces.IMovieOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieOrderService implements IMovieOrderService {
    @Autowired
    private MovieOrderRepository movieOrderRepository;

    @Override
    public List<MovieOrder> getAll() {
        return movieOrderRepository.findAll();
    }

    @Override
    public MovieOrder getById(MovieOrder movieOrder) {
        return movieOrderRepository.findById(movieOrder.getMovieOrderId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(MovieOrder movieOrder) {
        try {
            movieOrderRepository.save(movieOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already exists!");
        }
    }

    @Override
    public ResponseEntity<String> update(MovieOrder movieOrder) {
        Optional<MovieOrder> movieOrderOptional = movieOrderRepository.findById(movieOrder.getMovieOrderId());
        if (movieOrderOptional.isPresent()) {
            try {
                movieOrderRepository.save(movieOrder);
                return ResponseEntity.status(HttpStatus.OK).body("Movie updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already exists!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found!");
        }
    }

    @Override
    public ResponseEntity<String> delete(MovieOrder movieOrder) {
        MovieOrder existedMovieOrder = this.getById(movieOrder);
        if (existedMovieOrder != null) {
            movieOrderRepository.delete(existedMovieOrder);
            return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found!");
        }
    }

}
