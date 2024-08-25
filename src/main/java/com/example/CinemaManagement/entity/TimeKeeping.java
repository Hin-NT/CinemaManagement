package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_timekeeping")
@NoArgsConstructor
public class TimeKeeping {
    @Id
    @Column(name = "timekeeping_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timekeepingId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public TimeKeeping(LocalDateTime startTime, LocalDateTime endTime, Account account) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.account = account;
    }
}
