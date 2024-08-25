package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.ProductOrderDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrderDetailDTO {
    private double price;
    private int quantity;
    private ProductDTO product;

    public ProductOrderDetailDTO(ProductOrderDetail productOrderDetail) {
        price = productOrderDetail.getPrice();
        quantity = productOrderDetail.getQuantity();

        product = new ProductDTO(productOrderDetail.getProduct());
    }
}
