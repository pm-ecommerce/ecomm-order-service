package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery, Integer> {
}
