package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_feedback")
@NoArgsConstructor
public class Feedback {
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Feedback(String comment, LocalDateTime feedbackDate, Account account, Movie movie) {
        this.comment = comment;
        this.feedbackDate = feedbackDate;
        this.account = account;
        this.movie = movie;
    }
}
