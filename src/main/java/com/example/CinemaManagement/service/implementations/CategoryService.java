package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Category;
import com.example.CinemaManagement.entity.Movie;
import com.example.CinemaManagement.repository.CategoryRepository;
import com.example.CinemaManagement.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Category category) {
        return categoryRepository.findById(category.getCategoryId()).orElse(null);
    }

    @Override
    public ResponseEntity<Category> add(Category category) {
        try {
            Category categoryAdded = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryAdded);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Category());
        }
    }

    @Override
    public ResponseEntity<String> update(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(category.getCategoryId());
        if (categoryOptional.isPresent()) {
            try {
                Category categoryUpdated = categoryRepository.save(category);
                return ResponseEntity.status(HttpStatus.OK).body("Update category is success: " + categoryUpdated.getCategoryId());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update category is failure");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update category failure because not found");
        }
    }

    @Override
    public ResponseEntity<String> delete(Category category) {
        Category existingCategory = this.getById(category);
        if (existingCategory != null) {
            try {
                categoryRepository.delete(existingCategory);
                return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with ID: " + category.getCategoryId());
        }
    }

    @Override
    public ResponseEntity<String> delete(Category category, boolean isDelete) {
        Category existingCategory = this.getById(category);

        if (existingCategory != null) {
            if(isDelete) {
                try {
                    categoryRepository.delete(existingCategory);
                    return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully!");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete seat due to: " + e.getMessage());
                }
            } else {
                List<String> movies = existingCategory.getMovieList().stream()
                        .map(Movie::getTitle)
                        .toList();
                return ResponseEntity.ok(movies.toString());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with ID: " + category.getCategoryId());
        }
    }

    public List<Category> findByCategoryName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

}


