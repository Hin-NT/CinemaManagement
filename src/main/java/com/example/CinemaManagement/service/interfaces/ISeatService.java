package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.Seat;

public interface ISeatService extends IService<Seat>{
    boolean isSeatExist(String id);
}
