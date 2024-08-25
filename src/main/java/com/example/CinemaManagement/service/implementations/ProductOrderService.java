package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Product;
import com.example.CinemaManagement.entity.ProductOrder;
import com.example.CinemaManagement.entity.ProductOrderDetail;
import com.example.CinemaManagement.repository.ProductOrderDetailRepository;
import com.example.CinemaManagement.repository.ProductOrderRepository;
import com.example.CinemaManagement.repository.ProductRepository;
import com.example.CinemaManagement.service.interfaces.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderService implements IProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderDetailRepository productOrderDetailRepository;

    @Override
    public List<ProductOrder> getAll() {
        return productOrderRepository.findAll();
    }

    @Override
    public ProductOrder getById(ProductOrder productOrder) {
        return productOrderRepository.findById(productOrder.getOrderProductId()).orElse(null);
    }

    @Override
    public List<ProductOrder> getByAccountId(int accountId) {
        return productOrderRepository.findByAccountId(accountId);
    }

    @Override
    public ResponseEntity<String> add(ProductOrder productOrder) {
        try {
            ProductOrder data = new ProductOrder();
            data.setOrderDate(LocalDateTime.now());
            data.setAccount(productOrder.getAccount());
            data.setStatus(productOrder.getStatus());
            ProductOrder productOrderSave = productOrderRepository.save(data);

            List<ProductOrderDetail> productOrderDetails = productOrder.getProductOrderDetailList().stream()
                    .map(detail -> {
                        Product product = productRepository.findById(detail.getProduct().getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found: " + detail.getProduct().getProductId()));

                        ProductOrderDetail.ProductOrderDetailId productOrderDetailId = new ProductOrderDetail.ProductOrderDetailId(productOrderSave.getOrderProductId(), product.getProductId());

                        ProductOrderDetail productOrderDetail = new ProductOrderDetail();
                        productOrderDetail.setId(productOrderDetailId);
                        productOrderDetail.setProductOrder(productOrderSave);
                        productOrderDetail.setProduct(product);
                        productOrderDetail.setQuantity(detail.getQuantity());
                        productOrderDetail.setPrice(detail.getPrice());

                        return productOrderDetail;
                    }).collect(Collectors.toList());
            productOrderDetailRepository.saveAll(productOrderDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(ProductOrder productOrder) {
        ProductOrder newProductOrder = productOrderRepository.findById(productOrder.getOrderProductId())
                .orElseThrow(() -> new RuntimeException("Order not found: " + productOrder.getOrderProductId()));
        newProductOrder.setAccount(productOrder.getAccount());
        newProductOrder.setOrderDate(LocalDateTime.now());
        newProductOrder.setStatus(productOrder.getStatus());

        productOrderDetailRepository.deleteAll(newProductOrder.getProductOrderDetailList());

        List<ProductOrderDetail> productOrderDetails = productOrder.getProductOrderDetailList().stream()
                .map(detail -> {
                    Product product = productRepository.findById(detail.getProduct().getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + detail.getProduct().getProductId()));

                    ProductOrderDetail.ProductOrderDetailId productOrderDetailId = new ProductOrderDetail.ProductOrderDetailId(newProductOrder.getOrderProductId(), product.getProductId());

                    ProductOrderDetail productOrderDetail = new ProductOrderDetail();
                    productOrderDetail.setId(productOrderDetailId);
                    productOrderDetail.setProductOrder(newProductOrder);
                    productOrderDetail.setProduct(product);
                    productOrderDetail.setQuantity(detail.getQuantity());
                    productOrderDetail.setPrice(detail.getPrice());

                    return productOrderDetail;
                }).collect(Collectors.toList());

        productOrderDetailRepository.saveAll(productOrderDetails);
        newProductOrder.getProductOrderDetailList().clear();
        newProductOrder.getProductOrderDetailList().addAll(productOrderDetails);
//        https://codippa.com/how-to-resolve-a-collection-with-cascadeall-delete-orphan-was-no-longer-referenced-by-the-owning-entity-instance/
        productOrderRepository.save(newProductOrder);
        return ResponseEntity.status(HttpStatus.OK).body("Product Order updated successfully!");
    }

    @Override
    public ResponseEntity<String> delete(ProductOrder product) {
        ProductOrder existedProduct = this.getById(product);
        if (existedProduct != null) {
            productOrderRepository.delete(existedProduct);
            return ResponseEntity.status(HttpStatus.OK).body("Product Order deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Order not found!");
        }
    }

}
