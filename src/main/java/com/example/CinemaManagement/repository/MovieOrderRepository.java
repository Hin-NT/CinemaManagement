package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.MovieOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieOrderRepository extends JpaRepository<MovieOrder, String> {

}
