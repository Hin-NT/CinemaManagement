package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.TimeKeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, Integer> {
    @Query("SELECT tk FROM TimeKeeping tk WHERE tk.account = :account AND tk.startTime BETWEEN :start AND :end")
    List<TimeKeeping> findByAccountAndStartTimeBetween(@Param("account") Account account,
                                                       @Param("start") LocalDateTime start,
                                                       @Param("end") LocalDateTime end);

    @Query(value = "SELECT * FROM tbl_timekeeping tk WHERE tk.account_id = :accountId AND DATE(tk.start_time) = :currentDate", nativeQuery = true)
    Optional<TimeKeeping> findByAccountIdAndCurrentDate(@Param("accountId") int accountId,
                                                        @Param("currentDate") LocalDate currentDate);

}
