package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.SeatDTO;
import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.service.interfaces.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/seats")
@Validated
public class SeatController {

    @Autowired
    private ISeatService seatService;

    // Fetch all seats
    @GetMapping("")
    public ResponseEntity<List<SeatDTO>> getAllSeats() {
        List<Seat> seats = seatService.getAll();
        List<SeatDTO> accountDTOList = seats.stream().map(SeatDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOList);
    }

    // Fetch a seat by its ID
    @GetMapping("/{seatId}")
    public ResponseEntity<SeatDTO> getSeatById(@PathVariable String seatId) {
        Seat createSeat = new Seat();

        Seat seat = seatService.getById(createSeat);
        return ResponseEntity.ok(new SeatDTO(seat));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("")
    public ResponseEntity<?> createSeat(@Valid @RequestBody Seat seat) {
        return seatService.add(seat);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("")
    public ResponseEntity<String> updateSeat(@Valid @RequestBody Seat seat) {
        return seatService.update(seat);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/{seatId}")
    public ResponseEntity<String> deleteSeat(@PathVariable String seatId) {
        Seat createSeat = new Seat();
        createSeat.setSeatId(seatId);
        return seatService.delete(createSeat);
    }

}
