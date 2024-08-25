package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TimeKeeping;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TimeKeepingDTO {
    private int timekeepingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public TimeKeepingDTO(TimeKeeping timekeeping) {
        if (timekeeping != null) {
            this.timekeepingId = timekeeping.getTimekeepingId();
            this.startTime = timekeeping.getStartTime();
            this.endTime = timekeeping.getEndTime();
        }
    }
}
