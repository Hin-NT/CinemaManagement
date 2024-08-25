package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.TicketOrderDetailDTO;
import com.example.CinemaManagement.entity.TicketOrderDetail;
import com.example.CinemaManagement.service.implementations.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ticket-order-detail")
public class TicketOrderDetailController {

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @GetMapping("")
    public ResponseEntity<List<TicketOrderDetailDTO>> getAllTicketOrders() {
        List<TicketOrderDetail> ticketOrderList = ticketOrderDetailService.getAll();
        List<TicketOrderDetailDTO> ticketOrderDTOList = ticketOrderList.stream()
                .map(ticketOrder -> new TicketOrderDetailDTO(ticketOrder, 1))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ticketOrderDTOList);
    }

    @PostMapping("")
    public ResponseEntity<String> createTicketOrder(@RequestBody TicketOrderDetail ticketOrder) {
        return ticketOrderDetailService.add(ticketOrder);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<TicketOrderDetailDTO> getTicketOrderById(@PathVariable int id) {
//        TicketOrderDetail createTicket = new TicketOrderDetail();
//        createTicket.setTicketOrder();
//
//        TicketOrderDetail ticketOrder = ticketOrderDetailService.getById(createTicket);
//        TicketOrderDetailDTO ticketOrderDTO = new TicketOrderDetailDTO(ticketOrder, 1);
//
//        return ResponseEntity.ok(ticketOrderDTO);
//    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTicketOrder(@RequestBody TicketOrderDetail ticketOrder) {
        return ticketOrderDetailService.update(ticketOrder);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteTicketOrder(@PathVariable int id) {
//        TicketOrderDetail createTicket = new TicketOrderDetail();
//        createTicket.setTicketOrder(id);
//        return ticketOrderDetailService.delete(createTicket);
//    }

}
