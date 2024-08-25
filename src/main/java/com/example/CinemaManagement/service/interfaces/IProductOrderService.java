package com.example.CinemaManagement.service.interfaces;

import com.example.CinemaManagement.entity.ProductOrder;

import java.util.List;

public interface IProductOrderService extends IService<ProductOrder> {
    List<ProductOrder> getByAccountId(int accountId);

}
