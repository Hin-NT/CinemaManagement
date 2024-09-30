package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.FeedbackDTO;
import com.example.CinemaManagement.entity.Feedback;
import com.example.CinemaManagement.service.implementations.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAll();
        List<FeedbackDTO> feedbackDTOList = feedbacks.stream().map(FeedbackDTO::new).toList();
        return ResponseEntity.ok(feedbackDTOList);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable int feedbackId) {
        Feedback createdFeedback = new Feedback();
        createdFeedback.setFeedbackId(feedbackId);
        Feedback feedback = feedbackService.getById(createdFeedback);
        return ResponseEntity.ok(new FeedbackDTO(feedback));
    }

    @PostMapping("")
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) {
        return feedbackService.add(feedback);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFeedback(@Valid @RequestBody Feedback feedback) {
        return feedbackService.update(feedback);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@Valid @RequestBody Feedback feedback) {
        Feedback createdFeedback = new Feedback();
        createdFeedback.setFeedbackId(feedback.getFeedbackId());
        return feedbackService.delete(createdFeedback);
    }

}
