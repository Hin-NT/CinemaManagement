package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.Category;
import com.example.CinemaManagement.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.categoryName = :name")
    List<Category> findCategoryByName(@Param("name") String name);

    @Query(value = "SELECT * FROM tbl_movie_category mc WHERE mc.category_id = %:categoryId%", nativeQuery = true)
    List<Category> findMovieByCategory(int categoryId);
}
