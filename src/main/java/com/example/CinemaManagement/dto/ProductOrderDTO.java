package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.ProductOrder;
import com.example.CinemaManagement.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductOrderDTO {
    private int orderProductId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private int accountId;
    private List<ProductOrderDetailDTO> productOrderDetailList;

    public ProductOrderDTO(ProductOrder productOrder, int choose) {
        orderProductId = productOrder.getOrderProductId();
        orderDate = productOrder.getOrderDate();
        orderStatus = productOrder.getStatus();

        accountId = productOrder.getAccount().getAccountId();

        if (choose == 1) {
            productOrderDetailList = productOrder.getProductOrderDetailList()
                    .stream().map(ProductOrderDetailDTO::new)
                    .collect(Collectors.toList());
        }
    }


}
