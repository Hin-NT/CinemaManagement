package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.config.Utils;
import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.entity.TicketOrder;
import com.example.CinemaManagement.entity.TicketOrderDetail;
import com.example.CinemaManagement.repository.SeatRepository;
import com.example.CinemaManagement.repository.TicketOrderDetailRepository;
import com.example.CinemaManagement.repository.TicketOrderRepository;
import com.example.CinemaManagement.service.interfaces.ITicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketOrderService implements ITicketOrderService {

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired
    TicketOrderDetailRepository ticketOrderDetailRepository;

    @Autowired
    SeatRepository seatRepository;

    @Override
    public List<TicketOrder> getAll() {
        return ticketOrderRepository.findAll();
    }

    @Override
    public TicketOrder getById(TicketOrder ticketOrder) {
        return ticketOrderRepository.findById(ticketOrder.getOrderId()).orElse(null);
    }

    @Override
    public List<TicketOrder> getByAccountId(int accountId) {
        return ticketOrderRepository.findByAccountId(accountId);
    }

    @Override
    public ResponseEntity<String> add(TicketOrder ticketOrder) {
        try {
            TicketOrder ticketOrderData = new TicketOrder();
            ticketOrderData.setStatus(ticketOrder.getStatus());
            ticketOrderData.setOrderDate(LocalDateTime.now());
            ticketOrderData.setCode(Utils.encrypt(LocalDateTime.now() + ticketOrder.getTicketOrderDetailList().get(0).getSeat().getSeatId()));
            ticketOrderData.setAccount(ticketOrder.getAccount());
            ticketOrderData.setShowtime(ticketOrder.getShowtime());
            TicketOrder ticketOrderSaved = ticketOrderRepository.save(ticketOrderData);

            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
                            .map(detail -> {
                                Seat seat = seatRepository.findById(detail.getSeat().getSeatId())
                                        .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                                TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId = new TicketOrderDetail.TicketOrderDetailId(ticketOrderSaved.getOrderId(), seat.getSeatId());

                                TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
                                ticketOrderDetail.setId(ticketOrderDetailId);
                                ticketOrderDetail.setSeat(seat);
                                ticketOrderDetail.setTicketOrder(ticketOrderSaved);
                                ticketOrderDetail.setPrice(detail.getPrice());

                                return ticketOrderDetail;
                            }).collect(Collectors.toList());
            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);
            return ResponseEntity.status(HttpStatus.CREATED).body("TicketOrder created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(TicketOrder ticketOrder) {
        try {
            TicketOrder ticketOrderData = ticketOrderRepository.findById(ticketOrder.getOrderId())
                    .orElseThrow(() -> new RuntimeException("TicketOrder Not Found"));
            ticketOrderData.setStatus(ticketOrder.getStatus());
            ticketOrderData.setOrderDate(LocalDateTime.now());
            ticketOrderData.setAccount(ticketOrder.getAccount());
            ticketOrderData.setShowtime(ticketOrder.getShowtime());

            ticketOrderDetailRepository.deleteAll(ticketOrderData.getTicketOrderDetailList());

            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
                    .map(detail -> {
                        Seat seat = seatRepository.findById(detail.getSeat().getSeatId())
                                .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                        TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId = new TicketOrderDetail.TicketOrderDetailId(ticketOrder.getOrderId(), seat.getSeatId());

                        TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
                        ticketOrderDetail.setId(ticketOrderDetailId);
                        ticketOrderDetail.setSeat(seat);
                        ticketOrderDetail.setTicketOrder(ticketOrderData);
                        ticketOrderDetail.setPrice(detail.getPrice());

                        return ticketOrderDetail;
                    }).collect(Collectors.toList());
            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);
            ticketOrderData.getTicketOrderDetailList().clear();
            ticketOrderData.getTicketOrderDetailList().addAll(ticketOrderDetailList);
            ticketOrderRepository.save(ticketOrderData);
            return ResponseEntity.status(HttpStatus.OK).body("TicketOrder updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update ticket order due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> delete(TicketOrder ticketOrder) {
        TicketOrder existingTicketOrder = this.getById(ticketOrder);

        if (existingTicketOrder != null) {
            try {
                ticketOrderRepository.delete(existingTicketOrder);
                return ResponseEntity.status(HttpStatus.OK).body("Ticket Order deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete Ticket Order due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Order not found with ID: " + ticketOrder.getOrderId());
        }
    }

    public ResponseEntity<Double> getRevenueByChoose(int choose, int time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;

        switch (choose) {
            case 1:
                startDate = now.minusWeeks(time);
                break;
            case 2:
                startDate = now.minusMonths(time);
                break;
            case 3:
                startDate = now.minusYears(time);
                break;
            default:
                return ResponseEntity.status(HttpStatus.OK).body(0.0);
        }
        List<TicketOrder> ticketOrders = ticketOrderRepository.findAllByOrderDateBetween(startDate, now);

        return ResponseEntity.status(HttpStatus.OK).body(calculatorRevenue(ticketOrders));
    }

    public ResponseEntity<Double> getRevenueByDateBetween(String startTime, String endTime) {

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        List<TicketOrder> ticketOrders = ticketOrderRepository.findAllByOrderDateBetween(start, end);

        return ResponseEntity.status(HttpStatus.OK).body(calculatorRevenue(ticketOrders));
    }

    public double calculatorRevenue(List<TicketOrder> ticketOrders) {
        double totalRevenue = 0.0;

        for (TicketOrder ticketOrder : ticketOrders) {
            for (TicketOrderDetail detail : ticketOrder.getTicketOrderDetailList()) {
                totalRevenue += detail.getPrice();
            }
        }
        return totalRevenue;
    }

}
