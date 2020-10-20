package com.pm.ecommerce.order_service.entities;

import lombok.Data;

@Data
public class Customer {

    private String customerNumber;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Address address;
}
