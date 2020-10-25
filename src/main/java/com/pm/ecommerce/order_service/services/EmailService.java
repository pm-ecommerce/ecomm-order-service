package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.order_service.interfaces.IEmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Override
    public void sendNotification(String to, String body){
        System.out.println("test email sent");
    }
}
