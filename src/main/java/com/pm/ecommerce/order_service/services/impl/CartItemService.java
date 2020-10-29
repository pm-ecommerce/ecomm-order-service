package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.CartItem;
import com.pm.ecommerce.order_service.repositories.CartItemRepository;
import com.pm.ecommerce.order_service.services.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService implements ICartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ICartItemService cartItemService;

    @Override
    public CartItem getCartItemById(int cartItemId) {
        return cartItemService.getCartItemById(cartItemId);
    }

    @Override
    public Boolean checkTotalAmountCart(double totalAmount, int userId) {
        Optional<CartItem> total_amount = cartItemRepository.findById(userId);
        if (total_amount.equals(total_amount)){
            return true;
        }

        System.out.print("Error from request " + total_amount + " --db-- " + totalAmount);
        return false;
    }
}
