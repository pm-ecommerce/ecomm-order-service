package com.pm.ecommerce.order_service.model;

import com.pm.ecommerce.entities.Order;
import lombok.Data;

@Data
public class OrderResponse {
    int id;
    String name;
    double total;

    public OrderResponse(Order order) {
        id = order.getId();
        name = order.getUser().getName();
        total = order.getItems().stream().map(e -> e.getRate() * e.getQuantity()).reduce(0.0, Double::sum);
        total += (total * 0.07);
    }
}
