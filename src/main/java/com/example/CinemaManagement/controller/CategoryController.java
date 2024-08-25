package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.CategoryDTO;
import com.example.CinemaManagement.entity.Category;
import com.example.CinemaManagement.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDTO> categoryDTOList = categories.stream().map(CategoryDTO::new).toList();
        return ResponseEntity.ok(categoryDTOList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int categoryId) {
        Category createCategory = new Category();
        createCategory.setCategoryId(categoryId);
        Category category = categoryService.getById(createCategory);
        return ResponseEntity.ok(new CategoryDTO(category));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) {
        Category createCategory = new Category();
        createCategory.setCategoryId(categoryId);
        return categoryService.delete(createCategory);
    }

    @DeleteMapping("/test/{categoryId}")
    public ResponseEntity<String> test(@PathVariable int categoryId, @RequestParam boolean isDelete) {
        Category createCategory = new Category();
        createCategory.setCategoryId(categoryId);
        return categoryService.delete(createCategory, isDelete);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<CategoryDTO>> getCategoryByName(@RequestParam String categoryName) {
//        List<Category> categories = categoryService.findByCategoryName(categoryName);
//        List<CategoryDTO> categoryDTOList = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
//        return ResponseEntity.ok(categoryDTOList);
//    }

}