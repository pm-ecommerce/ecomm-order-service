package com.pm.ecommerce.order_service.notifications.events;

import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.context.ApplicationEvent;

public class ScheduledDeliveryEvent extends ApplicationEvent {
    private final ScheduledDelivery scheduledDelivery;

    public ScheduledDeliveryEvent(Object source, ScheduledDelivery message) {
        super(source);
        this.scheduledDelivery = message;
    }

    public ScheduledDelivery getDelivery(){
        return scheduledDelivery;
    }
}
