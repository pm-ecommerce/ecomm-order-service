package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.order_service.services.IOrderItemsService;
import com.pm.ecommerce.order_service.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OderItemsService implements IOrderItemsService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem registerOrder(OrderItem orderItems) {
        return orderItemRepository.save(orderItems);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem findById(int orderItemId) {
        Optional<OrderItem> result = orderItemRepository.findById(orderItemId);

        OrderItem orderItem = null;
        if (result.isPresent()) {
            orderItem = result.get();
        } else {
            throw new RuntimeException("Did not find order item id - " + orderItemId);
        }
        return orderItem;
    }
}
