package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.order_service.model.CartItemResponse;
import com.pm.ecommerce.order_service.model.OrderInput;

import java.util.List;

public interface IOrderService {
    Order checkoutOrder(OrderInput order) throws Exception;
    List<ScheduledDelivery> getUserOrders(int orderId);
    List<ScheduledDelivery> getVendorOrders(int vendorId);
    CartItemResponse deleteCartItem(int cartItemId, String sessionId) throws Exception;
}
