package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.TheaterDTO;
import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.service.interfaces.ISeatService;
import com.example.CinemaManagement.service.interfaces.ITheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/theaters")
public class TheaterController {

    @Autowired
    private ITheaterService theaterService;

    // Fetch all theaters
    @GetMapping("")
    public ResponseEntity<List<TheaterDTO>> getAllTheaters() {
        List<Theater> theaterList = theaterService.getAll();
        List<TheaterDTO> accountDTOList = theaterList.stream().map(TheaterDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOList);
    }

    // Create a new theater
    @PostMapping("")
    public ResponseEntity<?> createTheater(@RequestBody Theater theater) {
        return theaterService.add(theater);
    }

    // Fetch a theater by its ID
    @GetMapping("/{id}")
    public ResponseEntity<TheaterDTO> getTheaterById(@PathVariable int id) {
        Theater createTheater = new Theater();
        createTheater.setTheaterId(id);

        Theater theater = theaterService.getById(createTheater);
        if (theater != null) {
            return ResponseEntity.ok(new TheaterDTO(theater));
        }
        return ResponseEntity.notFound().build();
    }

    // Update an existing theater
    @PutMapping("")
    public ResponseEntity<String> updateTheater(@RequestBody Theater theater) {
        return theaterService.update(theater);
    }

    // Delete a theater by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheater(@PathVariable int id) {
        Theater createTheater = new Theater();
        createTheater.setTheaterId(id);
        return theaterService.delete(createTheater);
    }

    // Find theaters by name
    @GetMapping("/search")
    public ResponseEntity<List<TheaterDTO>> findTheaterByName(@RequestParam("theaterName") String theaterName) {
        return theaterService.getByName(theaterName);
    }

}
