package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.*;
import com.example.CinemaManagement.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TicketOrderDTO {
    private int orderId;
    private LocalDateTime orderDate;
    private int accountId;
    private ShowtimeDTO showtime;
    private double totalOrder;
    private OrderStatus orderStatus;
    private String theaterName;
    private String titleMovie;
    private int duration;
    private String code;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<TicketOrderDetailDTO> orderDetail;
    private TicketOrderDetail ticketOrderDetail;
    private Set<String> tickets;

    public TicketOrderDTO(TicketOrder ticketOrder, int choose) {
        this.orderId = ticketOrder.getOrderId();
        this.orderDate = ticketOrder.getOrderDate();
        this.orderStatus = ticketOrder.getStatus();
        this.code = ticketOrder.getCode();

        if(ticketOrder.getShowtime() != null) {
            Theater theater = ticketOrder.getShowtime().getTheater();
            Movie movie = ticketOrder.getShowtime().getMovie();

            this.theaterName = theater.getTheaterName();
            this.titleMovie = movie.getTitle();
            this.duration = movie.getDuration();
            this.startTime = ticketOrder.getShowtime().getStartTime();
            this.endTime = ticketOrder.getShowtime().getEndTime();

        }


//        if (choose == 1) {
//            this.orderDetail = ticketOrder.getTicketOrderDetailList() != null ?
//                    ticketOrder.getTicketOrderDetailList().stream()
//                            .map(ticketOrderDetail -> new TicketOrderDetailDTO(ticketOrderDetail, 0))
//                            .collect(Collectors.toList()) : null;
//        }

        this.tickets = ticketOrder.getTicketOrderDetailList() != null ?
                ticketOrder.getTicketOrderDetailList().stream()
                        .map(ticketOrderDetail -> ticketOrderDetail.getSeat().getSeatId()).collect(Collectors.toSet()) : null;

        this.totalOrder = ticketOrder.getTicketOrderDetailList() != null ?
                ticketOrder.getTicketOrderDetailList().stream()
                        .mapToDouble(TicketOrderDetail::getPrice)
                        .sum() : 0.0;
    }
}
