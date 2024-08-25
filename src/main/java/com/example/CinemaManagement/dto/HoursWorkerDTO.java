package com.example.CinemaManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
public class HoursWorkerDTO {
    LocalDate dateTime;
    LocalTime startTime;
    LocalTime endTime;
    Long hours;

    public HoursWorkerDTO(LocalDate dateTime, LocalTime startTime, LocalTime endTime, Long hours) {
        this.dateTime = dateTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hours = hours;
    }
}
