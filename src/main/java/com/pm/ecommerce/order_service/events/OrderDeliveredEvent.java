package com.pm.ecommerce.order_service.events;

import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.context.ApplicationEvent;

public class OrderDeliveredEvent extends ApplicationEvent {

    private final ScheduledDelivery delivery;

    public OrderDeliveredEvent(Object source, ScheduledDelivery delivery) {
        super(source);
        this.delivery = delivery;
    }

    public ScheduledDelivery getDelivery() {
        return delivery;
    }
}
