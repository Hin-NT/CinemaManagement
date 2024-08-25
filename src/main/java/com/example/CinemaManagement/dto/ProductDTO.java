package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private int productId;
    private String productName;
    private double price;
    private String image;

    public ProductDTO(Product product) {
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.image = product.getImage();
        }
    }
}
