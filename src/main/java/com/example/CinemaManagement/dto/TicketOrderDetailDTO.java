package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TicketOrderDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketOrderDetailDTO {
    private int orderId;
    private double price;
    private SeatDTO seat;
    private TicketOrderDTO ticketOrder;

    public TicketOrderDetailDTO(TicketOrderDetail ticketOrderDetail, int choose) {
        this.price = ticketOrderDetail.getPrice();
        this.seat = new SeatDTO(ticketOrderDetail.getSeat());

        if(choose == 1) {
            this.ticketOrder = new TicketOrderDTO(ticketOrderDetail.getTicketOrder(), 0);
        }

    }

    public TicketOrderDetailDTO(TicketOrderDetail ticketOrderDetail) {
        this.seat = new SeatDTO(ticketOrderDetail.getSeat());
    }
}
