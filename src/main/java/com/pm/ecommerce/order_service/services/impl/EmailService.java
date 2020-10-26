package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.order_service.services.IEmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Override
    public void sendNotification(String to, String body){
        System.out.println("sent email test");
    }
}
