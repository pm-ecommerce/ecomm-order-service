package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.enums.OrderItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery, Integer> {
    Page<ScheduledDelivery> findAllByUserIdAndStatusIn(int userId, List<OrderItemStatus> statusList, Pageable paging);

    Page<ScheduledDelivery> findAllByVendorIdAndStatusIn(int vendorId, List<OrderItemStatus> statusList, Pageable paging);

    Page<ScheduledDelivery> findAllByStatusIn(List<OrderItemStatus> statusList, Pageable paging);
}
