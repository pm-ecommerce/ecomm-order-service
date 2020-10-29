package com.pm.ecommerce.order_service.config;

import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.context.ApplicationEvent;

public class NewScheduledDeliveryEvent extends ApplicationEvent {
    private final ScheduledDelivery delivery;

    public NewScheduledDeliveryEvent(Object source, ScheduledDelivery message) {
        super(source);
        this.delivery = message;
    }

    public ScheduledDelivery getDelivery() {
        return delivery;
    }
}
