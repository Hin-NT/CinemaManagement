package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.config.Utils;
import com.example.CinemaManagement.entity.ShowTimeSeat;
import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.entity.TicketOrder;
import com.example.CinemaManagement.entity.TicketOrderDetail;
import com.example.CinemaManagement.enums.OrderStatus;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.repository.*;
import com.example.CinemaManagement.service.interfaces.ITicketOrderService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketOrderService implements ITicketOrderService {

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired
    TicketOrderDetailRepository ticketOrderDetailRepository;

    @Autowired
    TheaterSeatRepository theaterSeatRepository;

    @Autowired
    TheaterSeatService theaterSeatService;

    @Autowired
    ShowTimeSeatRepository showTimeSeatRepository;

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

//    @Override
//    public ResponseEntity<String> add(TicketOrder ticketOrder) {
//        try {
//            TicketOrder ticketOrderData = new TicketOrder();
//            ticketOrderData.setStatus(ticketOrder.getStatus());
//            ticketOrderData.setOrderDate(LocalDateTime.now());
//            ticketOrderData.setCode(Utils.encrypt(LocalDateTime.now() + String.valueOf(ticketOrder.getTicketOrderDetailList().get(0).getTheaterSeat().getId())));
//            ticketOrderData.setAccount(ticketOrder.getAccount());
//            ticketOrderData.setShowtime(ticketOrder.getShowtime());
//            TicketOrder ticketOrderSaved = ticketOrderRepository.save(ticketOrderData);
//
//            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
//                    .map(detail -> {
//                        TheaterSeat theaterSeat = theaterSeatRepository.findById(detail.getTheaterSeat().getId())
//                                .orElseThrow(() -> new RuntimeException("Seat Not Found"));
//
//                        theaterSeat.setSeatStatus(SeatStatus.CHOOSING);
//                        theaterSeatRepository.save(theaterSeat);
//
//                        TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId = new TicketOrderDetail.TicketOrderDetailId(ticketOrderSaved.getOrderId(), theaterSeat.getId());
//
//                        TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
//                        ticketOrderDetail.setId(ticketOrderDetailId);
//                        ticketOrderDetail.setTheaterSeat(theaterSeat);
//                        ticketOrderDetail.setTicketOrder(ticketOrderSaved);
//                        ticketOrderDetail.setPrice(detail.getPrice());
//
//                        return ticketOrderDetail;
//                    }).collect(Collectors.toList());
//            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);
//            return ResponseEntity.status(HttpStatus.CREATED).body("TicketOrder created successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> update(TicketOrder ticketOrder) {
//        try {
//            TicketOrder ticketOrderData = ticketOrderRepository.findById(ticketOrder.getOrderId())
//                    .orElseThrow(() -> new RuntimeException("TicketOrder Not Found"));
//            ticketOrderData.setStatus(ticketOrder.getStatus());
//            ticketOrderData.setOrderDate(LocalDateTime.now());
//            ticketOrderData.setAccount(ticketOrder.getAccount());
//            ticketOrderData.setShowtime(ticketOrder.getShowtime());
//
//            ticketOrderDetailRepository.deleteAll(ticketOrderData.getTicketOrderDetailList());
//
//            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
//                    .map(detail -> {
//                        TheaterSeat theaterSeat = theaterSeatRepository.findById(detail.getTheaterSeat().getId())
//                                .orElseThrow(() -> new RuntimeException("Seat Not Found"));
//
//                        TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId = new TicketOrderDetail.TicketOrderDetailId(ticketOrder.getOrderId(), theaterSeat.getId());
//
//                        TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
//                        ticketOrderDetail.setId(ticketOrderDetailId);
//                        ticketOrderDetail.setTheaterSeat(theaterSeat);
//                        ticketOrderDetail.setTicketOrder(ticketOrderData);
//                        ticketOrderDetail.setPrice(detail.getPrice());
//
//                        return ticketOrderDetail;
//                    }).collect(Collectors.toList());
//            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);
//            ticketOrderData.getTicketOrderDetailList().clear();
//            ticketOrderData.getTicketOrderDetailList().addAll(ticketOrderDetailList);
//            ticketOrderRepository.save(ticketOrderData);
//            return ResponseEntity.status(HttpStatus.OK).body("TicketOrder updated successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update ticket order due to: " + e.getMessage());
//        }
//    }

    @Override
    public ResponseEntity<TicketOrder> add(TicketOrder ticketOrder) {
        return ResponseEntity.ok(new TicketOrder());
    }

    @Override
    public ResponseEntity<?> create(TicketOrder ticketOrder) {
        try {
            // Tạo đối tượng TicketOrder mới
            TicketOrder ticketOrderData = new TicketOrder();
            ticketOrderData.setStatus(OrderStatus.PENDING);
            ticketOrderData.setOrderDate(LocalDateTime.now());
            ticketOrderData.setPaymentDeadline(LocalDateTime.now().plusMinutes(1)); 
            ticketOrderData.setCode(Utils.encrypt(LocalDateTime.now() + String.valueOf(ticketOrder.getTicketOrderDetailList().get(0).getShowTimeSeat().getId())));
            ticketOrderData.setAccount(ticketOrder.getAccount());
            ticketOrderData.setShowtime(ticketOrder.getShowtime());

            // Lưu TicketOrder
            TicketOrder ticketOrderSaved = ticketOrderRepository.save(ticketOrderData);

            // Tạo danh sách TicketOrderDetail từ danh sách chi tiết đơn hàng
            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
                    .map(detail -> {
                        ShowTimeSeat showTimeSeat = showTimeSeatRepository.findById(detail.getShowTimeSeat().getId())
                                .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                        TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId =
                                new TicketOrderDetail.TicketOrderDetailId(ticketOrderSaved.getOrderId(), showTimeSeat.getId());

                        TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
                        ticketOrderDetail.setId(ticketOrderDetailId);
                        showTimeSeat.setSeatStatus(SeatStatus.CHOOSING);
                        ticketOrderDetail.setShowTimeSeat(showTimeSeat);
                        ticketOrderDetail.setTicketOrder(ticketOrderSaved);
                        ticketOrderDetail.setPrice(detail.getPrice());


                        return ticketOrderDetail;
                    }).collect(Collectors.toList());

            // Lưu tất cả TicketOrderDetail
            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);

            return ResponseEntity.status(HttpStatus.CREATED).body(ticketOrderSaved.getOrderId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(TicketOrder ticketOrder) {
        try {
            TicketOrder ticketOrderData = ticketOrderRepository.findById(ticketOrder.getOrderId())
                    .orElseThrow(() -> new RuntimeException("TicketOrder Not Found"));

            // Cập nhật các chi tiết đơn hàng
            ticketOrderData.setStatus(ticketOrder.getStatus());
            ticketOrderData.setOrderDate(LocalDateTime.now());
            ticketOrderData.setPaymentDeadline(LocalDateTime.now().plusMinutes(1)); // Set payment deadline
            ticketOrderData.setAccount(ticketOrder.getAccount());
            ticketOrderData.setShowtime(ticketOrder.getShowtime());


            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList().stream()
                    .map(detail -> {
                        ShowTimeSeat showTimeSeat = showTimeSeatRepository.findById(detail.getShowTimeSeat().getId())
                                .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                        TicketOrderDetail.TicketOrderDetailId ticketOrderDetailId = new TicketOrderDetail.TicketOrderDetailId(ticketOrder.getOrderId(), showTimeSeat.getId());
                        TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
                        ticketOrderDetail.setId(ticketOrderDetailId);
                        ticketOrderDetail.setShowTimeSeat(showTimeSeat);
                        ticketOrderDetail.setTicketOrder(ticketOrderData);
                        ticketOrderDetail.setPrice(detail.getPrice());

                        return ticketOrderDetail;
                    }).collect(Collectors.toList());

            ticketOrderDetailRepository.saveAll(ticketOrderDetailList);


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

    @Transactional
    @Scheduled(fixedRate = 5000)
    public void checkPaymentDeadlines() {
        System.out.println("Run method check Payment Deadlines");
        try {
            // Tìm các đơn hàng đã quá hạn và chưa thanh toán
            List<TicketOrder> expiredOrders = ticketOrderRepository.findAllByPaymentDeadlineBeforeAndStatus(
                    LocalDateTime.now(), OrderStatus.PENDING);

            for (TicketOrder order : expiredOrders) {
                // Cập nhật trạng thái cho từng chi tiết đơn hàng
                for (TicketOrderDetail detail : order.getTicketOrderDetailList()) {

                    ShowTimeSeat showTimeSeat = detail.getShowTimeSeat();
                    showTimeSeat.setSeatStatus(SeatStatus.EMPTY);
                    showTimeSeatRepository.save(showTimeSeat);
                }

                // Cập nhật trạng thái đơn hàng thành CANCELED
                order.setStatus(OrderStatus.CANCELED);
                ticketOrderRepository.save(order);
            }
        } catch (Exception e) {
            System.err.println("Error checking payment deadlines: " + e.getMessage());
        }
    }

    @Override
    public double calculatorRevenue(List<TicketOrder> ticketOrders) {
        double totalRevenue = 0.0;

        for (TicketOrder ticketOrder : ticketOrders) {
            for (TicketOrderDetail detail : ticketOrder.getTicketOrderDetailList()) {
                if (ticketOrder.getStatus() != OrderStatus.CANCELED) {
                    totalRevenue += detail.getPrice();
                }
//                totalRevenue += detail.getPrice();
            }
        }
        return totalRevenue;
    }

    @Override
    public ResponseEntity<String> confirmOrderTicker(int orderId) {
        Optional<TicketOrder> existTicketOrder = ticketOrderRepository.findById(orderId);
        if (existTicketOrder.isPresent()) {
            TicketOrder ticketOrder = existTicketOrder.get();
            ticketOrder.setStatus(OrderStatus.COMPLETED);
            ticketOrder.setOrderDate(LocalDateTime.now());
            ticketOrderRepository.save(ticketOrder);

            List<TicketOrderDetail> ticketOrderDetailList = ticketOrder.getTicketOrderDetailList();

            for (TicketOrderDetail detail : ticketOrderDetailList) {

                ShowTimeSeat showTimeSeat = detail.getShowTimeSeat();
                showTimeSeat.setSeatStatus(SeatStatus.SELECTED);

                showTimeSeatRepository.save(showTimeSeat);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Ticket Order updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Order not found with ID: " + orderId);
        }
    }

}
