package com.pm.ecommerce.order_service.notifications.event_data;

import com.pm.ecommerce.entities.OrderItem;
import lombok.Data;

@Data
public class OrderItemEmail {
    String name;
    int quantity;
    double rate;
    double price;

    public OrderItemEmail(OrderItem item) {
        name = item.getProduct().getName();
        quantity = item.getQuantity();
        rate = item.getRate();
        price = rate * quantity;
    }
}
