package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Feedback;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedbackDTO {
    private int feedbackId;
    private String comment;
    private LocalDateTime feedbackDate;
    private AccountDTO account;
    private String movieTitle;

    public FeedbackDTO(Feedback feedback) {
        this.feedbackId = feedback.getFeedbackId();
        this.comment = feedback.getComment();
        this.feedbackDate = feedback.getFeedbackDate();
        this.account = new AccountDTO(feedback.getAccount());
        this.movieTitle = feedback.getMovie().getTitle();
    }
}
