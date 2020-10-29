package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.CartItem;
import org.springframework.stereotype.Service;

@Service
public interface ICartItemService {
    CartItem getCartItemById(int cartItemId);
    Boolean checkTotalAmountCart(double totalAmount, int userId);
}
