package com.pm.ecommerce.order_service.model;

import lombok.Data;

@Data
public class ChargeModel {
    int cardId;
    double amount;
}
