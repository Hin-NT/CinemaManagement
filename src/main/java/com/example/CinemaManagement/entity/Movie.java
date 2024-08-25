package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;

    @Column(name = "poster")
    private String poster;

    @Column(name = "trailer")
    private String trailer;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "director")
    private String director;

    @Lob
    @Column(name = "cast")
    private String cast;

    @Column(name = "producer")
    private String producer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private MovieStatus status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name ="label_id")
    private Label label;

    public Movie(String title, int duration, String poster, String trailer, String description, String director, String cast, String producer, LocalDate releaseDate, LocalDate endDate, MovieStatus status, Set<Category> categories, Label label) {
        this.title = title;
        this.duration = duration;
        this.poster = poster;
        this.trailer = trailer;
        this.description = description;
        this.director = director;
        this.cast = cast;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.status = status;
        this.categories = categories;
        this.label = label;
    }

    public Movie(int movieId, String title, int duration, String trailer, String description, String director, String producer, String cast, LocalDate releaseDate, LocalDate endDate, MovieStatus status, Set<Category> categories, Label label) {
        this.movieId = movieId;
        this.title = title;
        this.duration = duration;
        this.trailer = trailer;
        this.description = description;
        this.director = director;
        this.producer = producer;
        this.cast = cast;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.status = status;
        this.categories = categories;
        this.label = label;
    }
}
