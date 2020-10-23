package com.pm.ecommerce.order_service.interfaces;

import com.pm.ecommerce.entities.Account;
import com.pm.ecommerce.entities.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> addCartByUserIdAndProductId(int accountId, int cartItemId) throws Exception;
//    void updateQtyByCartId(long cartId,int qty,double price) throws Exception;
    List<Cart> getCartByUserId(long userId);
    List<Cart> removeCartByUserId(long cartId,long userId);
//    List<Cart> removeAllCartByUserId(long userId);
//    Boolean checkTotalAmountAgainstCart(double totalAmount,long userId);
//    List<Cart> getAllCheckoutByUserId(long userId);
//    List<Cart> saveProductsForCheckout(List<Cart> tmp)  throws Exception;

}
