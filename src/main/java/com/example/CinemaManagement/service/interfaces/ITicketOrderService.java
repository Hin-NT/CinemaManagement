package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.TicketOrder;
import com.example.CinemaManagement.enums.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ITicketOrderService extends IService<TicketOrder> {
    List<TicketOrder> getByAccountId(int accountId);

    ResponseEntity<Double> getRevenueByChoose(int choose, int time);

    ResponseEntity<Double> getRevenueByDateBetween(String startTime, String endTime);

    double calculatorRevenue(List<TicketOrder> ticketOrders);

    ResponseEntity<String> confirmOrderTicker(int orderId);

    ResponseEntity<?> create(TicketOrder ticketOrder);
}
