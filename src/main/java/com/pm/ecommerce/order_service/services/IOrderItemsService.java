package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.CartItem;
import com.pm.ecommerce.entities.OrderItem;

import java.util.List;

public interface IOrderItemsService {
    OrderItem registerOrderItem(OrderItem order);

    List<OrderItem> getAllOrderItems();

    OrderItem findById(int orderItemId);
}
