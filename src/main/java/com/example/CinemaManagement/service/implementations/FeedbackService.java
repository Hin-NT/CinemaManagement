package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Feedback;
import com.example.CinemaManagement.repository.FeedbackRepository;
import com.example.CinemaManagement.service.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements IService<Feedback> {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getAll() {
        return (List<Feedback>) feedbackRepository.findAll();
    }

    @Override
    public Feedback getById(Feedback feedback) {
        return feedbackRepository.findById(feedback.getFeedbackId()).orElse(null);
    }

    @Override
    public ResponseEntity<Feedback> add(Feedback feedback) {
        try {
            Feedback feedbackAdd = feedbackRepository.save(feedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackAdd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Feedback());
        }
    }

    @Override
    public ResponseEntity<String> update(Feedback feedback) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedback.getFeedbackId());
        if (optionalFeedback.isPresent()) {
           try {
               feedbackRepository.save(feedback);
               return ResponseEntity.status(HttpStatus.OK).body("Feedback updated successfully!");
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update feedback due to: " + e.getMessage());
           }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback not found!");
        }
    }

    @Override
    public ResponseEntity<String> delete(Feedback feedback) {
        Optional<Feedback> existingFeedback = feedbackRepository.findById(feedback.getFeedbackId());
        if (existingFeedback.isPresent()) {
            try {
                feedbackRepository.delete(existingFeedback.get());
                return ResponseEntity.status(HttpStatus.OK).body("Feedback deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete feedback due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback not found with ID: " + feedback.getFeedbackId());
        }
    }

}

