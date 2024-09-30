package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.Account;
import com.example.CinemaManagement.entity.TimeKeeping;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITimeKeepingService extends IService<TimeKeeping> {
    ResponseEntity<?> createOrUpdateTimeKeeping(Account account);
}
