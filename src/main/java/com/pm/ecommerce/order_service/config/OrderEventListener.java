package com.pm.ecommerce.order_service.config;

import com.pm.ecommerce.entities.Notification;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener implements ApplicationListener<NewOrderEvent> {
    @Override
    public void onApplicationEvent(NewOrderEvent orderEvent) {
        // create a notification here
        System.out.println(orderEvent.getOrder().getTax());
        Notification notification = new Notification();
        notification.setSender("sa.giri@miu.edu");
        notification.setReceiver("ioesandeep@gmail.com");
        notification.setSubject("We have received your order");
        // read the file contents
        // populate the file with real data
        // set the populated file as notification message
//        notification.setMessage(contentsOfFile);
//
//        repository.save(notification);
//
//        template.send("NotificationTopic", notification.getId());
    }
}
