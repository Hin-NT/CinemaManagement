package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.TimeKeepingDTO;
import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.entity.TimeKeeping;
import com.example.CinemaManagement.service.interfaces.ITimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/time-keeping")
public class TimeKeepingController {

    @Autowired
    private ITimeKeepingService timeKeepingService;

    @GetMapping("")
    public ResponseEntity<List<TimeKeepingDTO>> getAllTimeKeeping() {
        List<TimeKeeping> timeKeepingList = timeKeepingService.getAll();
        List<TimeKeepingDTO> timeKeepingDTOList = timeKeepingList.stream()
                .map(TimeKeepingDTO::new)
                .toList();
        return ResponseEntity.ok(timeKeepingDTOList);
    }

    @PostMapping("")
    public ResponseEntity<?> createTimeKeeping(@RequestBody TimeKeeping timeKeeping) {
        return timeKeepingService.add(timeKeeping);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeKeepingDTO> getTimeKeepingById(@PathVariable int id) {

        TimeKeeping createTimeKeeping = new TimeKeeping();
        createTimeKeeping.setTimekeepingId(id);

        TimeKeeping timeKeeping = timeKeepingService.getById(createTimeKeeping);
        TimeKeepingDTO timeKeepingDTO = new TimeKeepingDTO(timeKeeping);

        return ResponseEntity.ok(timeKeepingDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTimeKeeping(@RequestBody Account account) {
        return timeKeepingService.createOrUpdateTimeKeeping(account);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTimeKeeping(@PathVariable int id) {
        TimeKeeping timeKeeping = new TimeKeeping();
        timeKeeping.setTimekeepingId(id);
        return timeKeepingService.delete(timeKeeping);
    }

}
