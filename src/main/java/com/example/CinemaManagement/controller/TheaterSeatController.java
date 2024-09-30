package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.TheaterSeatDTO;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import com.example.CinemaManagement.service.interfaces.ITheaterSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/theater-seat")
public class TheaterSeatController {

    @Autowired
    private ITheaterSeatService theaterSeatService;

    // Fetch all theaters
    @GetMapping("")
    public ResponseEntity<List<TheaterSeatDTO>> getAllTheaters() {
        List<TheaterSeat> theaterList = theaterSeatService.getAll();

        if (theaterList == null || theaterList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TheaterSeatDTO> theaterSeatDTOList = theaterList.stream().map(TheaterSeatDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(theaterSeatDTOList);
    }

    // Create a new theater
    @PostMapping("")
    public ResponseEntity<?> createTheater(@RequestBody TheaterSeat theaterSeat) {
        return theaterSeatService.add(theaterSeat);
    }

    @PostMapping("/save/upload")
    public ResponseEntity<?> createTheaterSeatByFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        return theaterSeatService.addSeatByFile(file);
    }

    // Fetch a theater by its ID
    @GetMapping("/{id}")
    public ResponseEntity<List<TheaterSeatDTO>> getTheaterById(@PathVariable int id) {

        TheaterSeat createTheater = new TheaterSeat();
        createTheater.setId(id);

        List<TheaterSeat> theaterSeatList = theaterSeatService.findSeatsByTheater(id);
        List<TheaterSeatDTO> theaterSeatListDTO = theaterSeatList.stream().map(TheaterSeatDTO::new).toList();

        return ResponseEntity.ok(theaterSeatListDTO);
    }

    // Update an existing theater
    @PutMapping("/update")
    public ResponseEntity<String> updateTheater(@RequestBody TheaterSeat theater) {
        return theaterSeatService.update(theater);
    }

    // Delete a theater by its ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTheater(@PathVariable int id) {
        TheaterSeat createTheater = new TheaterSeat();
        createTheater.setId(id);
        return theaterSeatService.delete(createTheater);
    }

    @GetMapping("/seat/{theaterId}")
    public ResponseEntity<List<TheaterSeatDTO>> findSeatsByTheaterId(@PathVariable int theaterId) {
        List<TheaterSeat> seats = theaterSeatService.findSeatsByTheater(theaterId);
        List<TheaterSeatDTO> theaterSeatDTOList = seats.stream().map(TheaterSeatDTO::new).toList();
        return ResponseEntity.ok(theaterSeatDTOList);
    }

    //    http://localhost:8080/api/seat?theaterId=123&type=2
    @GetMapping("/type")
    public ResponseEntity<List<TheaterSeatDTO>> findSeatsByType(@RequestParam int theaterId, @RequestParam int type) {
        SeatType seatType;

        try {
            seatType = SeatType.values()[type];
        } catch (ArrayIndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body(List.of());
        }
        List<TheaterSeat> seats = theaterSeatService.findSeatsByType(theaterId, seatType);

        List<TheaterSeatDTO> accountDTOList = seats.stream().map(TheaterSeatDTO::new).toList();
        return ResponseEntity.ok(accountDTOList);
    }

    @GetMapping("/seat-count")
    public List<TheaterSeatDTO> getSeatCount() {
        return theaterSeatService.getSeatCountByTheater();
    }
}