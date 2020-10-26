package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.CartItem;

public interface ICartItemService {
    CartItem getCartItemById(int cartItemId);
}
