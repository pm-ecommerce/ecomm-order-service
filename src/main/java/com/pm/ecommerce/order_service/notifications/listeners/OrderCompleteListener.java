package com.pm.ecommerce.order_service.notifications.listeners;

import com.pm.ecommerce.entities.Notification;
import com.pm.ecommerce.order_service.notifications.config.QueueNotification;
import com.pm.ecommerce.order_service.notifications.config.TemplateParser;
import com.pm.ecommerce.order_service.notifications.event_data.EmailScheduledDelivery;
import com.pm.ecommerce.order_service.notifications.events.OrderCompleteEvent;
import com.pm.ecommerce.order_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompleteListener implements ApplicationListener<OrderCompleteEvent> {
    @Autowired
    QueueNotification queueNotification;

    @Autowired
    NotificationRepository repository;

    @Autowired
    TemplateParser parser;

    @Override
    public void onApplicationEvent(OrderCompleteEvent event) {
        Notification notification = new Notification();
        notification.setReceiver(event.getDelivery().getUser().getEmail());
        notification.setSubject("You order has been delivered.");
        String message = parser.parseTemplate("templates/order-complete", new EmailScheduledDelivery(event.getDelivery()));
        notification.setMessage(message);
        queueNotification.queue(notification);
    }
}
