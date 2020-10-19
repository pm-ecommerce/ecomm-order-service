package com.pm.ecommerce.order_service.entities;

import lombok.Data;

@Data
public class OrderLine {

    int quantity;
    Product product;
}
