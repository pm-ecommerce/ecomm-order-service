package com.pm.ecommerce.order_service.interfaces;

import com.pm.ecommerce.entities.OrderItem;

import java.util.List;

public interface IOrderItemsService {
    OrderItem registerOrder(OrderItem order);

    List<OrderItem> getAllOrderItems();

    OrderItem findById(int orderItemId);
}
