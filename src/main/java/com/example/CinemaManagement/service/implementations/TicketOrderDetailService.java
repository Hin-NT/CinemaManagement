package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.TicketOrderDetail;
import com.example.CinemaManagement.repository.TicketOrderDetailRepository;
import com.example.CinemaManagement.service.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderDetailService implements IService<TicketOrderDetail> {

    @Autowired
    TicketOrderDetailRepository ticketOrderDetailRepository;

    @Override
    public List<TicketOrderDetail> getAll() {
        return ticketOrderDetailRepository.findAll();
    }

    @Override
    public TicketOrderDetail getById(TicketOrderDetail ticketOrderDetail) {
        return ticketOrderDetailRepository.findById(ticketOrderDetail.getId().getOrderId()).orElse(null);
    }

    @Override
    public ResponseEntity<TicketOrderDetail> add(TicketOrderDetail ticketOrder) {
        try {
            TicketOrderDetail ticketOrderDetail = ticketOrderDetailRepository.save(ticketOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TicketOrderDetail());
        }
    }

    @Override
    public ResponseEntity<String> update(TicketOrderDetail ticketOrder) {
//        Optional<TicketOrderDetail> existingTicketOrder = ticketOrderDetailRepository.findById(ticketOrder.getOrderDetailId());
//        if (existingTicketOrder.isPresent()) {
//
//            try {
//                ticketOrderDetailRepository.save(ticketOrder);
//                return ResponseEntity.status(HttpStatus.OK).body("Ticket Order Detail updated successfully!");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Failed to update Ticket Order Detail due to: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Order not found with ID: " + ticketOrder.getOrderDetailId());
//        }
        return null;
    }

    @Override
    public ResponseEntity<String> delete(TicketOrderDetail ticketOrder) {
        TicketOrderDetail existingTicketOrder = this.getById(ticketOrder);

        if (existingTicketOrder != null) {
            try {
                ticketOrderDetailRepository.delete(existingTicketOrder);
                return ResponseEntity.status(HttpStatus.OK).body("Ticket Order Detail deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to delete Ticket Order Detail due to: " + e.getMessage());
            }
        } else {
            return null;
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Order Detail not found with ID: " + ticketOrder.getOrderDetailId());
        }
    }

}
