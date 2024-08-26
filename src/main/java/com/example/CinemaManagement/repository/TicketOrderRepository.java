package com.example.CinemaManagement.repository;

import com.example.CinemaManagement.entity.TicketOrder;
import com.example.CinemaManagement.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {
    @Query("SELECT to FROM TicketOrder to WHERE to.orderDate BETWEEN :startDate AND :endDate AND to.status=1")
    List<TicketOrder> findAllByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT order_id, order_date, status, account_id, showtime_id " +
            "FROM tbl_ticket_order WHERE account_id = %:accountId% ", nativeQuery = true)
    List<TicketOrder> findByAccountId(int accountId);

    List<TicketOrder> findAllByPaymentDeadlineBeforeAndStatus(LocalDateTime paymentDeadline, OrderStatus status);

}

