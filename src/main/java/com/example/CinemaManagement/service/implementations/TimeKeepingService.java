package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.TimeKeeping;
import com.example.CinemaManagement.repository.TimeKeepingRepository;
import com.example.CinemaManagement.service.interfaces.ITimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeKeepingService implements ITimeKeepingService {

    @Autowired
    private TimeKeepingRepository timeKeepingRepository;

    @Override
    public List<TimeKeeping> getAll() {
        return timeKeepingRepository.findAll();
    }

    @Override
    public TimeKeeping getById(TimeKeeping timeKeeping) {
        return timeKeepingRepository.findById(timeKeeping.getTimekeepingId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(TimeKeeping timeKeeping) {
        try {
            if (timeKeeping.getAccount() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create TimeKeeping due to account not found");
            }

            LocalDateTime time = LocalDateTime.now();

            timeKeeping.setStartTime(time);
            timeKeeping.setEndTime(time);

            timeKeepingRepository.save(timeKeeping);
            return ResponseEntity.status(HttpStatus.CREATED).body("TimeKeeping created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(TimeKeeping timeKeeping) {
        Optional<TimeKeeping> existingTimeKeeping = timeKeepingRepository.findById(timeKeeping.getTimekeepingId());

        System.out.println("TimeKeeping: " + timeKeeping.getTimekeepingId());

        if (existingTimeKeeping.isPresent()) {

            try {
                if (timeKeeping.getAccount() == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to update TimeKeeping due to account not found");
                }

                LocalDateTime endTime = LocalDateTime.now();

                timeKeeping.setStartTime(existingTimeKeeping.get().getStartTime());
                timeKeeping.setEndTime(endTime);

                timeKeepingRepository.save(timeKeeping);
                return ResponseEntity.status(HttpStatus.OK).body("TimeKeeping updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update TimeKeeping due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TimeKeeping not found with ID: " + timeKeeping.getTimekeepingId());
        }
    }

    @Override
    public ResponseEntity<String> delete(TimeKeeping timeKeeping) {
        TimeKeeping existingTimeKeeping = this.getById(timeKeeping);
        if (existingTimeKeeping != null) {
            try {
                timeKeepingRepository.delete(existingTimeKeeping);
                return ResponseEntity.status(HttpStatus.OK).body("TimeKeeping deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete TimeKeeping due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TimeKeeping not found with ID: " + timeKeeping.getTimekeepingId());
        }
    }

    @Override
    public ResponseEntity<String> createOrUpdateTimeKeeping(Account account) {

        LocalDate now = LocalDate.now();
        Optional<TimeKeeping> timeKeepingOptional = timeKeepingRepository.findByAccountIdAndCurrentDate(account.getAccountId(), now);

        if (timeKeepingOptional.isPresent()) {
            return update(timeKeepingOptional.get());
        } else {
            TimeKeeping timeKeeping = new TimeKeeping();
            timeKeeping.setAccount(account);
            return this.add(timeKeeping);
        }
    }

}
