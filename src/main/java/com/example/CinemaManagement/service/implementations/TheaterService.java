package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.dto.TheaterDTO;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.repository.TheaterRepository;
import com.example.CinemaManagement.service.interfaces.ITheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheaterService implements ITheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    // Fetch all theaters and convert them to DTOs for data transfer
    @Override
    public List<Theater> getAll() {
        return theaterRepository.findAll();
    }

    // Fetch a theater by its ID and convert it to DTO for viewing
    @Override
    public Theater getById(Theater theater) {
        return theaterRepository.findById(theater.getTheaterId()).orElse(null);
    }

    @Override
    public ResponseEntity<List<TheaterDTO>> getByName(String name) {
        List<Theater> theaters = theaterRepository.findByTheaterNameContainingIgnoreCase(name);
        List<TheaterDTO> theaterDTOList = theaters.isEmpty() ? new ArrayList<>() :
                theaters.stream()
                        .map(theater -> new TheaterDTO(theater))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(theaterDTOList);
    }

    @Override
    public ResponseEntity<String> add(Theater theater) {
        try {
            theaterRepository.save(theater);
            return ResponseEntity.status(HttpStatus.CREATED).body("Theater added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add theater due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Theater theater) {
        Optional<Theater> existingTheater = theaterRepository.findById(theater.getTheaterId());
        if (existingTheater.isPresent()) {
            try {
                theaterRepository.save(theater);
                return ResponseEntity.status(HttpStatus.OK).body("Theater updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update Theater due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater not found with ID: " + theater.getTheaterId());
        }
    }

    @Override
    public ResponseEntity<String> delete(Theater theater) {
        Theater exsitedTheater = this.getById(theater);
        if(exsitedTheater != null) {
            try {
                theaterRepository.delete(exsitedTheater);
                return ResponseEntity.status(HttpStatus.OK).body("Theater deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update theater due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater not found with ID: " + theater.getTheaterId());
        }
    }

    @Override
    public Theater isTheaterExist(String theaterName) {

        Optional<Theater> existTheater = theaterRepository.findByTheaterName(theaterName);
        return existTheater.orElse(null);
    }

}
