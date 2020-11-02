package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {
}
