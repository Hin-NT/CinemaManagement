package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.ProductOrderDTO;
import com.example.CinemaManagement.entity.ProductOrder;
import com.example.CinemaManagement.service.interfaces.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product-order")
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("")
    public ResponseEntity<List<ProductOrderDTO>> getAllProductOrders() {
        List<ProductOrder> products = productOrderService.getAll();
        List<ProductOrderDTO> productDTOList = products.stream().map(productOrder -> new ProductOrderDTO(productOrder, 0)).toList();
        return ResponseEntity.ok(productDTOList);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<ProductOrderDTO>> getAllProductOrdersByAccountId(@PathVariable int accountId) {
        List<ProductOrder> productOrder = productOrderService.getByAccountId(accountId);
        List<ProductOrderDTO> productOrderDTOList = productOrder.stream().map(products -> new ProductOrderDTO(products, 1)).toList();
        return ResponseEntity.ok(productOrderDTOList);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/{orderId}")
    public ResponseEntity<ProductOrderDTO> getProductOrderById(@PathVariable String order) {
        ProductOrder createProductOrder = new ProductOrder();
        createProductOrder.setOrderProductId(Integer.parseInt(order));
        ProductOrder product = productOrderService.getById(createProductOrder);
        return ResponseEntity.ok(new ProductOrderDTO(product, 1));
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE')")
    @PostMapping("")
    public ResponseEntity<String> createProductOrder(@Valid @RequestBody ProductOrder product) {
        return productOrderService.add(product);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductOrder product) {
        return productOrderService.update(product);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String order) {
        ProductOrder createProduct = new ProductOrder();
        createProduct.setOrderProductId(Integer.parseInt(order));
        return productOrderService.delete(createProduct);
    }
}
