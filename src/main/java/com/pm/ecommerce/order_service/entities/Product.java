package com.pm.ecommerce.order_service.entities;

import lombok.Data;

@Data
public class Product {

    String productnumber;
    double price;
    String description;
}
