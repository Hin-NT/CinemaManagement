package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.repository.SeatRepository;
import com.example.CinemaManagement.service.interfaces.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService implements ISeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat getById(Seat seat) {
        return seatRepository.findById(seat.getSeatId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(Seat seat) {
        try {
            seatRepository.save(seat);
            return ResponseEntity.status(HttpStatus.CREATED).body("Seat added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add seat due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Seat seat) {
        Optional<Seat> existingSeat = seatRepository.findById(seat.getSeatId());
        if (existingSeat.isPresent()) {
            try {
                seatRepository.save(seat);
                return ResponseEntity.status(HttpStatus.OK).body("Seat updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update account due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found with ID: " + seat.getSeatId());
        }
    }

    @Override
    public ResponseEntity<String> delete(Seat seat) {
        Seat exsitedSeat = this.getById(seat);
        if(exsitedSeat != null) {
            try {
                seatRepository.delete(exsitedSeat);
                return ResponseEntity.status(HttpStatus.OK).body("Seat deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found with ID: " + seat.getSeatId());
        }
    }

    @Override
    public boolean isSeatExist(String id) {
        return seatRepository.existsById(id);
    }

}

