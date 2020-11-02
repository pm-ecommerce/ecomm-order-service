package com.pm.ecommerce.order_service.notifications.events;

import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.context.ApplicationEvent;

public class OrderCompleteEvent extends ApplicationEvent {
    private final ScheduledDelivery delivery;

    public OrderCompleteEvent(Object source, ScheduledDelivery delivery) {
        super(source);
        this.delivery = delivery;
    }

    public ScheduledDelivery getDelivery() {
        return delivery;
    }
}
