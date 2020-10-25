package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.Cart;
import lombok.Data;

@Data
public class OrderInput {
    private Cart addCartRequest;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Integer deliveryDate;
}
