package com.pm.ecommerce.order_service.entities;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String city;
    private String zip;
    private String country;

}
