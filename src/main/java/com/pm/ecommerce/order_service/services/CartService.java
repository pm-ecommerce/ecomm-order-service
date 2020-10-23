package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Cart;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.order_service.interfaces.ICartService;
import com.pm.ecommerce.order_service.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Cart> addCartByUserIdAndProductId(int accountId, int cartItemId) throws Exception {
        return null;
    }

    @Override
    public List<Cart> getCartByUserId(long userId) {
        return null;
    }

    @Override
    public List<Cart> removeCartByUserId(long cartId, long userId) {
        return null;
    }

}
