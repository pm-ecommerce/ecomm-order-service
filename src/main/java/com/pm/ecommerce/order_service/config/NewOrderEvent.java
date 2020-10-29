package com.pm.ecommerce.order_service.config;

import com.pm.ecommerce.entities.Order;
import org.springframework.context.ApplicationEvent;

public class NewOrderEvent extends ApplicationEvent {
    private final Order order;

    public NewOrderEvent(Object source, Order message) {
        super(source);
        this.order = message;
    }

    public Order getOrder() {
        return order;
    }
}
