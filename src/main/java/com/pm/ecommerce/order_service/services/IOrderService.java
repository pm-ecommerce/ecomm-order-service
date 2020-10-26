package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Order;

import java.util.List;

public interface IOrderService {
    Order registerOrder(Order order); //

    List<Order> getAllOrders();

    Order findById(int orderId);

//    void placeOrder();
//    void cancelOrder();
}
