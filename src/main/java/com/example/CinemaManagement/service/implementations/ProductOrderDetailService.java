package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.ProductOrderDetail;
import com.example.CinemaManagement.repository.ProductOrderDetailRepository;
import com.example.CinemaManagement.service.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderDetailService implements IService<ProductOrderDetail> {

    @Autowired
    private ProductOrderDetailRepository repository;

    @Override
    public List<ProductOrderDetail> getAll() {
        return repository.findAll();
    }

    @Override
    public ProductOrderDetail getById(ProductOrderDetail product) {
        return null;
//        return repository.findById(product.getProductDetailId()).orElse(null);
    }

    @Override
    public ResponseEntity<ProductOrderDetail> add(ProductOrderDetail product) {
        try {
            ProductOrderDetail productOrderDetail = repository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductOrderDetail());
        }
    }

    @Override
    public ResponseEntity<String> update(ProductOrderDetail product) {
//        Optional<ProductOrderDetail> productOptional = repository.findById(product.getProductDetailId());
//
//        if (productOptional.isPresent()) {
//            try {
//                repository.save(product);
//                return ResponseEntity.status(HttpStatus.OK).body("Product Order Detail updated successfully!");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Product Order Detail due to: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Order Detail not found!");
//        }
        return null;
    }

    @Override
    public ResponseEntity<String> delete(ProductOrderDetail product) {
        ProductOrderDetail existedProduct = this.getById(product);
        if (existedProduct != null) {
            repository.delete(existedProduct);
            return ResponseEntity.status(HttpStatus.OK).body("Product Order Detail deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Order Detail not found!");
        }
    }

}
