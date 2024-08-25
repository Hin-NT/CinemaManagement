package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    @Query(value = "SELECT order_product_id, order_date, status, account_id " +
            "FROM tbl_product_order WHERE account_id = %:accountId% ", nativeQuery = true)
    List<ProductOrder> findByAccountId(int accountId);
}
