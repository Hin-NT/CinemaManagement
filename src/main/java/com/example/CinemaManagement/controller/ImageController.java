package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.entity.Image;
import com.example.CinemaManagement.entity.Product;
import com.example.CinemaManagement.repository.ImageRepository;
import com.example.CinemaManagement.service.implementations.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageRepository imageRepository;
    @PostMapping("")
    public void createProduct(
            @RequestParam("file") MultipartFile imageFile,
            @RequestParam("description") String description
    ) throws IOException {
        String imageUrl = cloudinaryService.uploadImage(imageFile);
        Image i = new Image(imageUrl, description);
        imageRepository.save(i);
    }
}
