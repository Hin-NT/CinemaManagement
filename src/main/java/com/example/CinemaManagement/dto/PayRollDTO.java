package com.example.CinemaManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PayRollDTO {
    private int accountId;
    private String userName;
    private int totalDayWorker;
    private double totalHourWorker;
    private double totalSalary;
    private List<HoursWorkerDTO> hoursWorkerDTO;

    public PayRollDTO(int accountId, String userName, int totalDayWorker, double totalHourWorker, double totalSalary, List<HoursWorkerDTO> hoursWorkerDTO) {
        this.accountId = accountId;
        this.userName = userName;
        this.totalDayWorker = totalDayWorker;
        this.totalHourWorker = totalHourWorker;
        this.totalSalary = totalSalary;
        this.hoursWorkerDTO = hoursWorkerDTO;
    }
}
