package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.dto.ProductDTO;
import com.example.CinemaManagement.entity.Product;
import com.example.CinemaManagement.repository.ProductRepository;
import com.example.CinemaManagement.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Product product) {
        return productRepository.findById(product.getProductId()).orElse(null);
    }

    @Override
    public ResponseEntity<Product> add(Product product) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(Product product) {
        return null;
    }

    @Override
    public ResponseEntity<String> add(Product product, MultipartFile imageFile) {
        try {
            String imageUrl = cloudinaryService.uploadImage(imageFile);
            product.setImage(imageUrl);
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Product product, MultipartFile imageFile) {
        Optional<Product> productOptional = productRepository.findById(product.getProductId());

        if (productOptional.isPresent()) {
            try {
                Product productUpdate = productOptional.get();
                if(imageFile != null) {
                    String imageUrl = cloudinaryService.uploadImage(imageFile);
                    productUpdate.setImage(imageUrl);
                }
                productUpdate.setProductName(product.getProductName());
                productUpdate.setPrice(product.getPrice());
                productRepository.save(productUpdate);
                return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Product due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
    }

    @Override
    public ResponseEntity<String> delete(Product product) {
        Product existedProduct = this.getById(product);
        if (existedProduct != null) {
            productRepository.delete(existedProduct);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
    }


    @Override
    public ResponseEntity<List<ProductDTO>> search(String keyword) {
        return null;
    }

}
