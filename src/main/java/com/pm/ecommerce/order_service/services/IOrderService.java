package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.order_service.model.CartItemResponse;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.model.PagedResponse;
import com.pm.ecommerce.order_service.model.ScheduledDeliveryResponse;

import java.util.List;

public interface IOrderService {
    Order checkoutOrder(OrderInput order) throws Exception;
    CartItemResponse deleteCartItem(int cartItemId, String sessionId) throws Exception;
    PagedResponse<ScheduledDeliveryResponse> getUserOrders(int userId, int page, int itemsPerPage, boolean b) throws Exception;
    PagedResponse<ScheduledDeliveryResponse> getVendorOrders(int vendorId, int page, int itemsPerPage, boolean b) throws Exception;
}
