package com.pm.ecommerce.order_service.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderInput {
    private int id;
    private String sessionId;
    private int shippingAddressId;
    private int billingAddressId;

    private String receiverName;
    private String receiverEmail;
    private String receiverPhone;

    List<ChargeModel> charges;
}
