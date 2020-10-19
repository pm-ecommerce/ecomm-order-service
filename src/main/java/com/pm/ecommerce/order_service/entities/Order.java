package com.pm.ecommerce.order_service.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;

@Data
public class Order {

    @GeneratedValue
    private int id;
}
