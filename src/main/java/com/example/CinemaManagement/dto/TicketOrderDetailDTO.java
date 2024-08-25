package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.entity.TicketOrderDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketOrderDetailDTO {
    private int orderId;
    private double price;
    private TheaterSeatDTO theaterSeat;
    private TicketOrderDTO ticketOrder;

    public TicketOrderDetailDTO(TicketOrderDetail ticketOrderDetail, int choose) {
        this.price = ticketOrderDetail.getPrice();
        this.theaterSeat = new TheaterSeatDTO(ticketOrderDetail.getTheaterSeat());
        if(choose == 1) {
            this.ticketOrder = new TicketOrderDTO(ticketOrderDetail.getTicketOrder(), 0);
        }

    }
}
