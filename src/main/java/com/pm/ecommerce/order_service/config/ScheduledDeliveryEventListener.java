package com.pm.ecommerce.order_service.config;

import com.pm.ecommerce.entities.Notification;
import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDeliveryEventListener implements ApplicationListener<NewScheduledDeliveryEvent> {
    @Override
    public void onApplicationEvent(NewScheduledDeliveryEvent deliveryEvent) {
        ScheduledDelivery delivery = deliveryEvent.getDelivery();
        // create a notification here
        System.out.println(delivery.getId());
        // create an email (notification) and save it in database
        // then send it to kakfa

    }
}
