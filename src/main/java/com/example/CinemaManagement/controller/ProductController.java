package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.ProductDTO;
import com.example.CinemaManagement.entity.Product;
import com.example.CinemaManagement.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/public/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductDTO> productDTOList = products.stream().map(ProductDTO::new).toList();
        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/public/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int productId) {
        Product createProduct = new Product();
        createProduct.setProductId(productId);
        Product product = productService.getById(createProduct);
        return ResponseEntity.ok(new ProductDTO(product));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("")
    public ResponseEntity<String> createProduct(
            @RequestParam("productName") String name,
            @RequestParam("price") double price,
            @RequestParam("imageFile") MultipartFile imageFile)
    {
        Product product = new Product();
        product.setProductName(name);
        product.setPrice(price);
        return productService.add(product, imageFile);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
        return productService.update(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/{product}")
    public ResponseEntity<String> deleteProduct(@PathVariable int product) {
        Product createProduct = new Product();
        createProduct.setProductId(product);
        return productService.delete(createProduct);
    }

}
