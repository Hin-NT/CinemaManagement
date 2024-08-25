package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_product_order")
@Getter
@Setter
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private int orderProductId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrderDetail> productOrderDetailList;

    public ProductOrder(int orderProductId, LocalDateTime orderDate, Account account, List<ProductOrderDetail> productOrderDetailList) {
        this.orderProductId = orderProductId;
        this.orderDate = orderDate;
        this.account = account;
        this.productOrderDetailList = productOrderDetailList;
    }
}
