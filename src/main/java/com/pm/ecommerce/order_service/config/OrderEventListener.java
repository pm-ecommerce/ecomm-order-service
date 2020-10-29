package com.pm.ecommerce.order_service.config;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener implements ApplicationListener<NewOrderEvent> {
    @Override
    public void onApplicationEvent(NewOrderEvent orderEvent) {
        // create a notification here
        System.out.println(orderEvent.getOrder().getTax());
    }
}
