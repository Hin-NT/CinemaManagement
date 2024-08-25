package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.ProductOrderDetailDTO;
import com.example.CinemaManagement.entity.ProductOrderDetail;
import com.example.CinemaManagement.service.implementations.ProductOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product-order-detail")
public class ProductOrderDetailController {

    @Autowired
    private ProductOrderDetailService productOrderDetailService;

    @GetMapping("")
    public ResponseEntity<List<ProductOrderDetailDTO>> getAllProductOrderDetails() {
        List<ProductOrderDetail> products = productOrderDetailService.getAll();
        List<ProductOrderDetailDTO> productDTOList = products.stream().map(ProductOrderDetailDTO::new).toList();
        return ResponseEntity.ok(productDTOList);
    }

//    @GetMapping("/{order}")
//    public ResponseEntity<ProductOrderDetailDTO> getProductOrderById(@PathVariable int order) {
//        ProductOrderDetail createProductOrder = new ProductOrderDetail();
//        createProductOrder.setProductDetailId(order);
//        ProductOrderDetail product = productOrderDetailService.findById(createProductOrder);
//        return ResponseEntity.ok(new ProductOrderDetailDTO(product));
//    }

    @PostMapping("")
    public ResponseEntity<String> createProductOrder(@Valid @RequestBody ProductOrderDetail product) {
        return productOrderDetailService.add(product);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductOrderDetail product) {
        return productOrderDetailService.update(product);
    }

//    @DeleteMapping("/delete/{order}")
//    public ResponseEntity<String> deleteOrder(@PathVariable int order) {
//        ProductOrderDetail createProduct = new ProductOrderDetail();
//        createProduct.setProductDetailId(order);
//        return productOrderDetailService.delete(createProduct);
//    }

}
