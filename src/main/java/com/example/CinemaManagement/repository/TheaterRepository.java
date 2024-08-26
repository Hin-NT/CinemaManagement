package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    List<Theater> findByTheaterNameContainingIgnoreCase(String theaterName);

    Optional<Theater> findByTheaterName(String theaterName);
}
