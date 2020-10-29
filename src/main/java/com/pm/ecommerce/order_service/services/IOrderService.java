package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.CartItem;
import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.order_service.model.OrderInput;

import java.util.List;

public interface IOrderService {
    Order registerOrder(Order order); //

    List<Order> getAllOrders();

    Order findById(int orderId);

    Order checkout_order(OrderInput order) throws Exception;

    Boolean checkTotalAmountCart(double totalAmount, int userId, OrderInput orderInput);

    List<CartItem> saveProductsForCheckout(List<CartItem> cartItemList) throws Exception;

//    void placeOrder();
//    void cancelOrder();
}
