package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.enums.OrderItemStatus;
import com.pm.ecommerce.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery, Integer> {

//    @Query("Select sd from ScheduledDelivery sd where sd.vendor.id=:vendorId")
//    List<ScheduledDelivery> findAllByVendor(int vendorId);

    Page<ScheduledDelivery> findAllByUserIdAndStatusIn(int userId, List<OrderItemStatus> statusList, Pageable paging);
    Page<ScheduledDelivery> findAllByVendorIdAndStatusIn(int vendorId, List<OrderItemStatus> statusList, Pageable paging);


//    List<ScheduledDelivery> findAllByVendorAndStatusIn(int vendorId, OrderItemStatus status);


}
