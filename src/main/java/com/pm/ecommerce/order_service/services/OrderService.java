package com.pm.ecommerce.order_service.services;

//import com.pm.ecommerce.order_service.entities.Order;
import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.order_service.interfaces.IOrderService;
import com.pm.ecommerce.order_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order registerOrder(Order order) {
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
