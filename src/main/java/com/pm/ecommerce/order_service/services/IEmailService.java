package com.pm.ecommerce.order_service.services;

public interface IEmailService {
    void sendNotification(String to, String body);
}
