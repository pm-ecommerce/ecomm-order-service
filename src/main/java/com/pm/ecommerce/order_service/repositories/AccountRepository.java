package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.order_service.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
