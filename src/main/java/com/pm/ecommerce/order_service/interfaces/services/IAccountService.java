package com.pm.ecommerce.order_service.interfaces.services;

import com.pm.ecommerce.order_service.entities.Account;

public interface IAccountService {
    Account registerCustomer(Account account);
}
