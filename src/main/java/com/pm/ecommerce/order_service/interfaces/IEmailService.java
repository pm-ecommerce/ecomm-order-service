package com.pm.ecommerce.order_service.interfaces;

public interface IEmailService {
    void sendNotification(String to, String body);
}
