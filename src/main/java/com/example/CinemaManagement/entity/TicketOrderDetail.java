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
    @MapsId("seatId")
    @JoinColumn(name = "seat_id", insertable = false, updatable = false)
    private Seat seat;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private TicketOrder ticketOrder;

    public TicketOrderDetail(int orderId, String seatId, double price) {
        this.id = new TicketOrderDetailId(orderId, seatId);
        this.price = price;
    }

    @Embeddable
    public static class TicketOrderDetailId implements Serializable {
        @Column(name = "order_id")
        private int orderId;

        @Column(name = "seat_id")
        private String seatId;

        public TicketOrderDetailId() {}

        public TicketOrderDetailId(int orderId, String seatId) {
            this.orderId = orderId;
            this.seatId = seatId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getSeatId() {
            return seatId;
        }

        public void setSeatId(String seatId) {
            this.seatId = seatId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TicketOrderDetailId that = (TicketOrderDetailId) o;
            return orderId == that.orderId && seatId.equals(that.seatId);
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(orderId);
            result = 31 * result + seatId.hashCode();
            return result;
        }
    }
}