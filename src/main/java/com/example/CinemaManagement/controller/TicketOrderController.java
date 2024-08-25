package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.TicketOrderDTO;
import com.example.CinemaManagement.entity.TicketOrder;
import com.example.CinemaManagement.service.interfaces.ITicketOrderService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ticket-order")
public class TicketOrderController {

    @Autowired
    private ITicketOrderService ticketOrderService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("")
    public ResponseEntity<List<TicketOrderDTO>> getAllTicketOrders() {
        List<TicketOrder> ticketOrderList = ticketOrderService.getAll();
        List<TicketOrderDTO> ticketOrderDTOList = ticketOrderList.stream()
                .map(ticketOrder -> new TicketOrderDTO(ticketOrder, 1))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ticketOrderDTOList);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TicketOrderDTO>> getAllTicketOrdersByAccountId(@PathVariable int accountId) {
        List<TicketOrder> ticketOrders = ticketOrderService.getByAccountId(accountId);
        List<TicketOrderDTO> ticketOrderDTOList = ticketOrders.stream().map(ticketOrder -> new TicketOrderDTO(ticketOrder, 1)).toList();
        return ResponseEntity.ok(ticketOrderDTOList);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE')")
    @PostMapping("")
    public ResponseEntity<String> createTicketOrder(@RequestBody TicketOrder ticketOrder) {
        return ticketOrderService.add(ticketOrder);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<TicketOrderDTO> getTicketOrderById(@PathVariable int id) {
        TicketOrder createTicket = new TicketOrder();
        createTicket.setOrderId(id);
        TicketOrder ticketOrder = ticketOrderService.getById(createTicket);
        TicketOrderDTO ticketOrderDTO = new TicketOrderDTO(ticketOrder, 1);

        return ResponseEntity.ok(ticketOrderDTO);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTicketOrder(@RequestBody TicketOrder ticketOrder) {
        return ticketOrderService.update(ticketOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicketOrder(@PathVariable int id) {
        TicketOrder createTicket = new TicketOrder();
        createTicket.setOrderId(id);
        return ticketOrderService.delete(createTicket);
    }

    @GetMapping("/revenue/by-date")
    public ResponseEntity<Double> getRevenueWithStartAndEndTime(@RequestBody Map<String, String> revenue) {
        String startTime = revenue.get("start");
        String endTime = revenue.get("end");
        return ticketOrderService.getRevenueByDateBetween(startTime, endTime);
    }

    @GetMapping("/revenue/by-choose")
    public ResponseEntity<Double> getRevenueWithChoose(@RequestParam int choose, @RequestParam int time) {
        return ticketOrderService.getRevenueByChoose(choose, time);
    }

}
