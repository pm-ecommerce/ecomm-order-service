package com.pm.ecommerce.order_service.repositories;


import com.pm.ecommerce.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
