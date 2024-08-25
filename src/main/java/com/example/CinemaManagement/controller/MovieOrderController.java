package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.MovieOrderDTO;
import com.example.CinemaManagement.entity.MovieOrder;
import com.example.CinemaManagement.service.interfaces.IMovieOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/movie-order")
public class MovieOrderController {

    @Autowired
    private IMovieOrderService movieOrderService;

    @GetMapping("")
    public ResponseEntity<List<MovieOrderDTO>> getAllMovieOrders() {
        List<MovieOrder> movieOrders = movieOrderService.getAll();
        List<MovieOrderDTO> movieOrderDTOList = movieOrders.stream().map(MovieOrderDTO::new).toList();
        return ResponseEntity.ok(movieOrderDTOList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<MovieOrderDTO> getMovieOrderById(@PathVariable("orderId") String orderId) {
        MovieOrder createMovieOrder = new MovieOrder();
        createMovieOrder.setMovieOrderId(orderId);
        MovieOrder movieOrder = movieOrderService.getById(createMovieOrder);
        return ResponseEntity.ok(new MovieOrderDTO(movieOrder));
    }

    @PostMapping("")
    public ResponseEntity<String> createMovieOrder(@Valid @RequestBody MovieOrder movieOrder) {
        return movieOrderService.add(movieOrder);
    }

    @PutMapping("")
    public ResponseEntity<String> updateMovieOrder(@Valid @RequestBody MovieOrder movieOrder) {
        return movieOrderService.update(movieOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteMovieOrder(@PathVariable("orderId") String orderId) {
        MovieOrder createMovieOrder = new MovieOrder();
        createMovieOrder.setMovieOrderId(orderId);
        return movieOrderService.delete(createMovieOrder);
    }

}
