package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.dto.ProductDTO;
import com.example.CinemaManagement.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService extends IService<Product> {
    ResponseEntity<List<ProductDTO>> search(String keyword);
    ResponseEntity<String> add(Product p, MultipartFile file);
    ResponseEntity<String> update(Product p, MultipartFile file);
}
