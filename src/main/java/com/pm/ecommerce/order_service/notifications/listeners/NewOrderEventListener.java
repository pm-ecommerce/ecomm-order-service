package com.pm.ecommerce.order_service.notifications.listeners;

import com.pm.ecommerce.entities.Notification;
import com.pm.ecommerce.order_service.notifications.config.QueueNotification;
import com.pm.ecommerce.order_service.notifications.config.TemplateParser;
import com.pm.ecommerce.order_service.notifications.event_data.UserOrderReceived;
import com.pm.ecommerce.order_service.notifications.events.NewOrderEvent;
import com.pm.ecommerce.order_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewOrderEventListener implements ApplicationListener<NewOrderEvent> {
    @Autowired
    QueueNotification queueNotification;

    @Autowired
    NotificationRepository repository;

    @Autowired
    TemplateParser parser;

    @Override
    public void onApplicationEvent(NewOrderEvent orderEvent) {
        Notification notification = new Notification();
        notification.setReceiver(orderEvent.getOrder().getUser().getEmail());
        notification.setSubject("We have received your order");
        String message = parser.parseTemplate("templates/order-received", new UserOrderReceived(orderEvent.getOrder()));
        notification.setMessage(message);
        queueNotification.queue(notification);
    }
}
