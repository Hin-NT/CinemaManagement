package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private List<MovieDTO> movies;

    public CategoryDTO(Category category, int choose) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();

        if (choose == 1) {
            this.movies = category.getMovieList().stream()
                    .map(movie -> new MovieDTO(movie, 0))
                    .collect(Collectors.toList());
        } else {
            this.movies = null;
        }
    }
    public Category toEntity() {
        Category category = new Category();
        category.setCategoryId(this.categoryId);
        category.setCategoryName(this.categoryName);
        return category;
    }

    public CategoryDTO(Category category) {
        this(category, 0);
    }
}
