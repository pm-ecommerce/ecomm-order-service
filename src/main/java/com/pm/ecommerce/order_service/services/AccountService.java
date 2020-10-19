package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.order_service.entities.Account;
import com.pm.ecommerce.order_service.interfaces.services.IAccountService;
import com.pm.ecommerce.order_service.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository repository;

    public Account registerCustomer(Account account){
        return repository.save(account);
    }
}
