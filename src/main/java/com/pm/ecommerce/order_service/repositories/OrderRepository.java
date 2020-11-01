package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    List<Order> findAllByUserId (Integer userid);

    List<Order> findOrdersByUser(User user);
}