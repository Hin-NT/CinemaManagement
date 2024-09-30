package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.TheaterSeat;
import com.example.CinemaManagement.entity.TicketOrderDetail;
import com.example.CinemaManagement.enums.SeatStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketOrderDetailDTO {
    private int orderId;
    private double price;
    private ShowTimeSeatDTO showTimeSeat;
    private TicketOrderDTO ticketOrder;
    private SeatStatus seatStatus; // Thêm thuộc tính này để lưu trạng thái ghế

    public TicketOrderDetailDTO(TicketOrderDetail ticketOrderDetail, int choose) {
        this.orderId = ticketOrderDetail.getId().getOrderId();
        this.price = ticketOrderDetail.getPrice();
        this.showTimeSeat = new ShowTimeSeatDTO(ticketOrderDetail.getShowTimeSeat());

        if (choose == 1) {
            this.ticketOrder = new TicketOrderDTO(ticketOrderDetail.getTicketOrder(), 0);
        }
    }
}
