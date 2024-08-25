package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.ShowtimeDTO;
import com.example.CinemaManagement.entity.Showtime;
import com.example.CinemaManagement.service.interfaces.IShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/showtime")
public class ShowtimeController {

    @Autowired
    private IShowtimeService showtimeService;

    @GetMapping("")
    public ResponseEntity<List<ShowtimeDTO>> getAllShowTimes() {
        List<Showtime> showtimeList = showtimeService.getAll();
        List<ShowtimeDTO> showtimeDTOList = showtimeList.stream().map(showtime -> new ShowtimeDTO(showtime, 0)).toList();
        return ResponseEntity.ok(showtimeDTOList);
    }

    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeDTO> getShowtimeById(@PathVariable int showtimeId) {
        Showtime createrShowtime = new Showtime();
        createrShowtime.setShowtimeId(showtimeId);
        Showtime showtime = showtimeService.getById(createrShowtime);
        return ResponseEntity.ok(new ShowtimeDTO(showtime, 1));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("")
    public ResponseEntity<String> createShowtime(@RequestBody Showtime showtime) {
        System.out.println(showtime.getTheater().getTheaterId());
        return showtimeService.add(showtime);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("")
    public ResponseEntity<String> updateShowtime(@Valid @RequestBody Showtime showtime) {
        return showtimeService.update(showtime);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<String> deleteShowtime(@PathVariable int showtimeId) {
        Showtime showtime = new Showtime();
        showtime.setShowtimeId(showtimeId);
        return showtimeService.delete(showtime);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowtimeDTO>> getAllShowTimesByMovie(@PathVariable int movieId) {
        List<Showtime> showtimeList = showtimeService.findShowTimesByMovie(movieId);

        List<ShowtimeDTO> showtimeDTOList = showtimeList.stream().map(ShowtimeDTO::new).toList();
        return ResponseEntity.ok(showtimeDTOList);
    }

//    @GetMapping("/movie")
//    public ResponseEntity<List<ShowtimeDTO>> getAllShowTimesMovieAndDate(@RequestParam int movieId, @RequestParam String date) {
//        List<Showtime> showtimeList = showtimeService.getShowTimesForMovieOnDate(movieId, date);
//
//        List<ShowtimeDTO> showtimeDTOList = showtimeList.stream().map(showtime -> new ShowtimeDTO(showtime, 0)).toList();
//        return ResponseEntity.ok(showtimeDTOList);
//    }

}
