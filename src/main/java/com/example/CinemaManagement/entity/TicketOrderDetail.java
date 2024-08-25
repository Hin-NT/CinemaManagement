package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_ticket_order_detail")
@Getter
@Setter
@NoArgsConstructor
public class TicketOrderDetail {

    @EmbeddedId
    private TicketOrderDetailId id;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @MapsId("theaterSeatId") // Use the correct field name for mapping
    @JoinColumn(name = "theater_seat_id", insertable = false, updatable = false) // Adjusted to match the field name
    private TheaterSeat theaterSeat;

    @ManyToOne
    @MapsId("orderId") // Correct mapping
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private TicketOrder ticketOrder;

    public TicketOrderDetail(int orderId, int seatId, double price) {
        this.id = new TicketOrderDetailId(orderId, seatId);
        this.price = price;
    }

    @Embeddable
    public static class TicketOrderDetailId implements Serializable {
        @Column(name = "order_id")
        private int orderId;

        @Column(name = "theater_seat_id")
        private int theaterSeatId;

        public TicketOrderDetailId() {}

        public TicketOrderDetailId(int orderId, int theaterSeatId) {
            this.orderId = orderId;
            this.theaterSeatId = theaterSeatId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getSeatId() {
            return theaterSeatId;
        }

        public void setSeatId(int seatId) {
            this.theaterSeatId = seatId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TicketOrderDetailId that = (TicketOrderDetailId) o;
            return orderId == that.orderId && theaterSeatId == that.theaterSeatId; // Corrected logic
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(orderId);
            result = 31 * result + Integer.hashCode(theaterSeatId); // Corrected method
            return result;
        }
    }
}