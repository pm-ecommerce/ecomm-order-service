package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.Cart;
import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.entities.OrderItemAttribute;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.order_service.services.IOrderItemsService;
import com.pm.ecommerce.order_service.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OderItemsService implements IOrderItemsService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Override
    public OrderItem registerOrderItem(OrderItem orderItem) {
//
//        List<OrderItemAttribute> orderItemAttributesList = orderItem.getAttributes().stream()
//                .map(e->{
//                    OrderItemAttribute attribute = new OrderItemAttribute();
//                    attribute.setOption(e.getOption());
//                    attribute.setName(e.getName());
//                    return attribute;
//                }).collect(Collectors.toList());
//        orderItem.setAttributes(orderItemAttributesList);
//        orderItem.setQuantity(orderItem.getQuantity());
//        orderItem.setRate(orderItem.getRate());


        // orderItem
//            OrderItem orderItem1 = new OrderItem();
//            orderItem1.setId(orderItem.getId());
//            orderItem1.setRate(orderItem.getRate());
//            orderItem1.setQuantity(orderItem.getQuantity());
//            orderItem1.setAttributes(orderItemAttributeArrayList);
//            orderItem1.setProduct(orderItem.getProduct());

        return orderItemRepository.save(orderItem);
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
