package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.order_service.model.OrderInput;

public interface IOrderService {
    Order checkoutOrder(OrderInput order) throws Exception;
}
