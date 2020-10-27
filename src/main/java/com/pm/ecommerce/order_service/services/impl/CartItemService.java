package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.CartItem;
import com.pm.ecommerce.order_service.services.ICartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements ICartItemService {

    @Override
    public CartItem getCartItemById(int cartItemId) {
        return null;
    }
}
