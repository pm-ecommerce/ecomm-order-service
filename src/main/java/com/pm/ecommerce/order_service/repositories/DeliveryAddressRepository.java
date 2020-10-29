package com.pm.ecommerce.order_service.repositories;


import com.pm.ecommerce.entities.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {

}
