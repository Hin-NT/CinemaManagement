package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.dto.TheaterDTO;
import com.example.CinemaManagement.entity.Theater;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITheaterService extends IService<Theater> {
    ResponseEntity<List<TheaterDTO>> getByName(String name);

    Theater isTheaterExist(String theaterName);

}
