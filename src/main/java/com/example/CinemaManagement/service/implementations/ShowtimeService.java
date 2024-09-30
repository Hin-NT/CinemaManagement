package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.dto.ShowtimeDTO;
import com.example.CinemaManagement.entity.*;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.repository.ShowTimeSeatRepository;
import com.example.CinemaManagement.repository.ShowtimeRepository;
import com.example.CinemaManagement.repository.TheaterRepository;
import com.example.CinemaManagement.service.interfaces.IShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowtimeService implements IShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowTimeSeatRepository showTimeSeatRepository;

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

            int theaterId = showtime.getTheater().getTheaterId();
            Theater theater = theaterRepository.findById(theaterId).orElse(null);

            if (theater == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Theater");
            }
            Movie movie = movieService.getById(showtime.getMovie());

            LocalDateTime endTime = showtime.getStartTime().plusMinutes(movie.getDuration());
            showtime.setEndTime(endTime);

            List<Showtime> conflictingShowTimes = showtimeRepository.findConflictingShowTimes(theaterId, showtime.getStartTime(), endTime);

            if (!conflictingShowTimes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a showtime scheduled during this period.");
            }

            Showtime showtimeAdded = showtimeRepository.save(showtime);

            List<TheaterSeat> theaterSeats = theater.getTheaterSeats();
            for (TheaterSeat theaterSeat : theaterSeats) {
                ShowTimeSeat showTimeSeat = new ShowTimeSeat(showtimeAdded, theaterSeat, SeatStatus.EMPTY);
                showTimeSeatRepository.save(showTimeSeat);
            }

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

    @Override
    public Map<LocalDate, List<ShowtimeDTO>> getMoviesByShowDates() {
        List<LocalDate> localDates = showtimeRepository.findAllUniqueShowDates().stream()
                .map(Date::toLocalDate)
                .toList();
        for (LocalDate localDate : localDates) {
            System.out.println("Ngày: " + localDate);
        }

        Map<LocalDate, List<ShowtimeDTO>> result = new HashMap<>();

        for (LocalDate date : localDates) {
            List<Showtime> showtimes = showtimeRepository.findShowTimesByDate(date);

            List<ShowtimeDTO> showtimeDTOs = showtimes.stream()
                    .map(showtime -> new ShowtimeDTO(showtime, 0))
                    .collect(Collectors.toList());

            result.put(date, showtimeDTOs);
        }

        return result;

    }

    public List<Showtime> getShowTimesForMovieOnDate(int movieId, String date) {

        LocalDateTime dateTime = LocalDateTime.parse(date);

        LocalDateTime startTime = dateTime.toLocalDate().atStartOfDay(); // Bắt đầu ngày
        LocalDateTime endTime = dateTime.toLocalDate().atTime(23, 59, 59); // Kết thúc ngày

        return showtimeRepository.findShowTimesByMovieIdAndDate(movieId, startTime, endTime);
    }

    public LocalDateTime getEndTime(Showtime showtime) {
        Movie movie = movieService.getById(showtime.getMovie());
        LocalDateTime startTime = showtime.getStartTime(); // lấy thời gian bắt đầu
        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration()); // thời gian bắt đầu + thời lượng phim chiếu = thời gian kết thức
        return endTime;
    }

    @Override
    public Map<String, List<ShowtimeDTO>> getShowTimesForMovieOnDate(int movieId, LocalDate date) {
        // Lấy tất cả suất chiếu cho bộ phim trong ngày và sắp xếp
        List<Showtime> showtimes = showtimeRepository.findShowTimesByMovieIdAndDateSorted(movieId, date);

        return showtimes.stream()
                .map(ShowtimeDTO::new)
                .collect(Collectors.groupingBy(showtimeDTO -> showtimeDTO.getTheater().getTheaterName()));
    }
}
