package com.pm.ecommerce.order_service.model;

import lombok.Data;

@Data
public class Charge {
    int cardId;
    double amount;
}
