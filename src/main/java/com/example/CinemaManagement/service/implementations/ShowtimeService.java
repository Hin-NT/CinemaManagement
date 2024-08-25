package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Movie;
import com.example.CinemaManagement.entity.Showtime;
import com.example.CinemaManagement.repository.ShowtimeRepository;
import com.example.CinemaManagement.service.interfaces.IShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService implements IShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieService movieService;

    @Override
    public List<Showtime> getAll() {
        return showtimeRepository.findAll();
    }

    @Override
    public Showtime getById(Showtime showtime) {
        return showtimeRepository.findById(showtime.getShowtimeId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(Showtime showtime) {
        try {

            showtime.setEndTime(getEndTime(showtime));

            showtimeRepository.save(showtime);
            return ResponseEntity.status(HttpStatus.CREATED).body("Showtime created successfully! ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add seat due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Showtime showtime) {
        Optional<Showtime> showtimeOptional = showtimeRepository.findById(showtime.getShowtimeId());
        if (showtimeOptional.isPresent()) {
            try {

                showtime.setEndTime(getEndTime(showtime));
                showtimeRepository.save(showtime);
                return ResponseEntity.status(HttpStatus.OK).body("Showtime updated successfully! ");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update showtime due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showtime not found!");
        }
    }

    @Override
    public ResponseEntity<String> delete(Showtime showtime) {
        Showtime existedShowtime = this.getById(showtime);
        if (existedShowtime != null) {
            try {
                showtimeRepository.delete(showtime);
                return ResponseEntity.status(HttpStatus.OK).body("Showtime deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete showtime due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showtime not found!");
        }
    }

    @Override
    public List<Showtime> findShowTimesByMovie(int movieID) {
        return showtimeRepository.findAllByMovie(movieID);
    }

    public List<Showtime> getShowTimesForMovieOnDate(int movieId, String date) {

        LocalDateTime dateTime = LocalDateTime.parse(date);

        LocalDateTime startTime = dateTime.toLocalDate().atStartOfDay(); // Bắt đầu ngày
        LocalDateTime endTime = dateTime.toLocalDate().atTime(23, 59, 59); // Kết thúc ngày

        // Gọi phương thức trong repository để lấy lịch chiếu
        return showtimeRepository.findShowTimesByMovieIdAndDate(movieId, startTime, endTime);
    }

    public LocalDateTime getEndTime(Showtime showtime) {
        Movie movie = movieService.getById(showtime.getMovie());
        LocalDateTime startTime = showtime.getStartTime(); // lấy thời gian bắt đầu
        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration()); // thời gian bắt đầu + thời lượng phim chiếu = thời gian kết thức
        return endTime;
    }

}
