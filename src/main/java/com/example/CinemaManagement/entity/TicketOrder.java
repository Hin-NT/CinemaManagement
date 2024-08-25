package com.example.CinemaManagement.entity;

import com.example.CinemaManagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_ticket_order")
@Getter
@Setter
@NoArgsConstructor
public class TicketOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @Column(name = "code")
    private String code;

    @Column(name = "payment_deadline")
    private LocalDateTime paymentDeadline;

    @OneToMany(mappedBy = "ticketOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketOrderDetail> ticketOrderDetailList;

    public TicketOrder(int orderId, LocalDateTime orderDate, Account account, Showtime showtime) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.account = account;
        this.showtime = showtime;
    }
}
