package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.dto.TheaterSeatDTO;
import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import com.example.CinemaManagement.repository.SeatRepository;
import com.example.CinemaManagement.repository.TheaterRepository;
import com.example.CinemaManagement.repository.TheaterSeatRepository;
import com.example.CinemaManagement.service.interfaces.ITheaterSeatService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class TheaterSeatService implements ITheaterSeatService {

    @Autowired
    private TheaterSeatRepository theaterSeatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<TheaterSeat> getAll() {
        return null;
    }

    @Override
    public TheaterSeat getById(TheaterSeat theaterSeat) {
        return theaterSeatRepository.findById(theaterSeat.getId()).orElse(null);
    }

    @Override
    public ResponseEntity<TheaterSeat> add(TheaterSeat theaterSeat) {
        try {
            TheaterSeat theaterSeatAdd = theaterSeatRepository.save(theaterSeat);
            return ResponseEntity.status(HttpStatus.CREATED).body(theaterSeatAdd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TheaterSeat());
        }
    }

    @Override
    public ResponseEntity<String> update(TheaterSeat theaterSeat) {
        Optional<TheaterSeat> existingSeat = theaterSeatRepository.findById(theaterSeat.getId());

        if (existingSeat.isPresent()) {
            try {
                theaterSeatRepository.save(theaterSeat);
                return ResponseEntity.status(HttpStatus.OK).body("Seat updated new Theater successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found with ID: " + theaterSeat.getId());
        }
    }

    @Override
    public ResponseEntity<String> delete(TheaterSeat theaterSeat) {
        TheaterSeat existedSeat = this.getById(theaterSeat);
        if (existedSeat != null) {
            try {
                theaterSeatRepository.delete(existedSeat);
                return ResponseEntity.status(HttpStatus.OK).body("Seat deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found with ID: " + theaterSeat.getId());
        }
    }

    @Override
    public List<TheaterSeat> findSeatsByTheater(int threadID) {
        return theaterSeatRepository.findSeatsByTheaterId(threadID);
    }

    @Override
    public List<TheaterSeat> findSeatsByType(int threadID, SeatType seatType) {
        return theaterSeatRepository.findSeatsByType(threadID, seatType);
    }

    @Override
    public ResponseEntity<?> addSeatByFile(MultipartFile file) {
        List<TheaterSeat> theaterSeatsAdded = new ArrayList<TheaterSeat>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String rowStart = row.getCell(0).getStringCellValue().trim();
                String rowEnd = row.getCell(1).getStringCellValue().trim();
                int numberSeat = (int) row.getCell(2).getNumericCellValue();
                int seatType = (int) row.getCell(3).getNumericCellValue();
                String theaterName = row.getCell(4).getStringCellValue().trim();
                int price = (int) row.getCell(5).getNumericCellValue();

                if(rowStart.isEmpty() || rowEnd.isEmpty() || numberSeat < 0 || seatType < 0 || theaterName.isEmpty()) {
                    break;
                }

                char charRowEnd = rowEnd.charAt(0);

                char seatStart = rowStart.charAt(0);
                int numberOfSeatStart = Integer.parseInt(rowStart.substring(1));

                int theaterID;
                Optional<Theater> theater = theaterRepository.findByTheaterName(theaterName);
                if (theater.isPresent()) {
                    theaterID = theater.get().getTheaterId();
                } else {
                    Theater newTheater = theaterRepository.save(new Theater(theaterName));
                    theaterID = newTheater.getTheaterId();
                    System.out.println("Theater not found");
                }

                for (char seatRow = seatStart; seatRow <= charRowEnd; seatRow++) {
                    for (int i = numberOfSeatStart; i < numberSeat + numberOfSeatStart; i++) {
                        String seatId = seatRow + String.valueOf(i);
                        if (!seatRepository.existsById(seatId)) {
                            seatRepository.save(new Seat(seatId));
                            System.out.println("Seat not found");
                        }

                        TheaterSeat theaterSeat = new TheaterSeat(new Seat(seatId), new Theater(theaterID), SeatType.values()[seatType], price);
                        TheaterSeat theaterSeatAdded = theaterSeatRepository.save(theaterSeat);
                        theaterSeatsAdded.add(theaterSeatAdded);
                    }
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
        }
        return ResponseEntity.status(HttpStatus.OK).body(theaterSeatsAdded);
    }

    @Override
    public List<TheaterSeatDTO> getSeatCountByTheater() {
        List<Object[]> results = theaterSeatRepository.countSeatsByTypeAndTheater();
        Map<Integer, TheaterSeatDTO> seatCountMap = new HashMap<>();

        for (Object[] result : results) {
            Integer theaterId = (Integer) result[0];
            String theaterName = (String) result[1];
            SeatType seatType = (SeatType) result[2];
            Long seatCount = (Long) result[3];

            seatCountMap
                    .computeIfAbsent(theaterId, k -> new TheaterSeatDTO(theaterId, theaterName, new HashMap<>()))
                    .getSeatCounts()
                    .put(seatType, seatCount.intValue());
        }

        return new ArrayList<>(seatCountMap.values());
    }

}
