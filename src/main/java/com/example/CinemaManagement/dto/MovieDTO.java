package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MovieDTO {
    private int movieId;
    private String title;
    private int duration;
    private String poster;
    private String trailer;
    private String description;
    private String director;
    private String cast;
    private String producer;
    private LocalDate releaseDate;
    private LocalDate endDate;
    private int labelId;
    private int status;
    private Set<CategoryDTO> categories;

    public MovieDTO(Movie movie, int choose) {
        this.movieId = movie.getMovieId();
        this.title = movie.getTitle();
        this.duration = movie.getDuration();
        this.poster = movie.getPoster();
        this.trailer = movie.getTrailer();
        this.description = movie.getDescription();
        this.director = movie.getDirector();
        this.cast = movie.getCast();
        this.producer = movie.getProducer();
        this.releaseDate = movie.getReleaseDate();
        this.endDate = movie.getEndDate();
        this.labelId = movie.getLabel().getLabelId();
        this.status = movie.getStatus().ordinal();

        if (choose == 1) {
            this.categories = movie.getCategories().stream()
                    .map(category -> new CategoryDTO(category, 0))
                    .collect(Collectors.toSet());

        }
    }

}
