package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tbl_product_order_detail")
@Getter
@Setter
@NoArgsConstructor
public class ProductOrderDetail {
    @EmbeddedId
    private ProductOrderDetailId id;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderProductId")
    @JoinColumn(name = "order_product_id", insertable = false, updatable = false)
    private ProductOrder productOrder;

    public ProductOrderDetail(ProductOrderDetailId id, ProductOrder productOrder, Product product, double price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productOrder = productOrder;
    }

    @Embeddable
    public static class ProductOrderDetailId implements Serializable {
        @Column(name = "order_product_id")
        private int orderProductId;

        @Column(name = "product_id")
        private int productId;

        public ProductOrderDetailId() {}

        public ProductOrderDetailId(int orderProductId, int productId) {
            this.orderProductId = orderProductId;
            this.productId = productId;
        }

        public int getOrderProductId() {
            return orderProductId;
        }

        public void setOrderProductId(int orderProductId) {
            this.orderProductId = orderProductId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductOrderDetailId that = (ProductOrderDetailId) o;
            return orderProductId == that.orderProductId && Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderProductId, productId);
        }
    }
}
