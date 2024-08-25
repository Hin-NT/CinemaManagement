package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.Category;
import org.springframework.http.ResponseEntity;

public interface ICategoryService extends IService<Category> {
    ResponseEntity<String> delete(Category category, boolean isDelete);
}
