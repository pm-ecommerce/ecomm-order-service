package com.pm.ecommerce.order_service.model;

import com.pm.ecommerce.entities.Cart;
import com.pm.ecommerce.entities.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderInput {
    private List<CartItem> cartItems;
    private int id;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Integer deliveryDate;

}
