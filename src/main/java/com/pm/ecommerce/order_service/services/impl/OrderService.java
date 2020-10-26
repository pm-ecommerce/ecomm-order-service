package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.controllers.OrderInput;
import com.pm.ecommerce.order_service.repositories.OrderItemRepository;
import com.pm.ecommerce.order_service.repositories.OrderRepository;
import com.pm.ecommerce.order_service.repositories.TransactionRepository;
import com.pm.ecommerce.order_service.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.*;

import static com.pm.ecommerce.enums.OrderStatus.RECEIVED;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVendorService vendorService;

    @Autowired
    private IEmailService emailService;



    public int getOrderId() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    public Order registerOrder(Order order) {
        if (order!=null) {
            order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            order.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(int orderId) {
        Optional<Order> result = orderRepository.findById(orderId);

        Order order = null;
        if (result.isPresent()) {
            order = result.get();
        } else {
            throw new RuntimeException("Did not find order id - " + orderId);
        }
        return order;
    }




}
