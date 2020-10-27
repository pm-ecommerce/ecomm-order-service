package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.repositories.CartRepository;
import com.pm.ecommerce.order_service.repositories.OrderItemRepository;
import com.pm.ecommerce.order_service.repositories.ScheduledDeliveryRepository;
import com.pm.ecommerce.order_service.repositories.TransactionRepository;
import com.pm.ecommerce.order_service.services.*;
import com.pm.ecommerce.order_service.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping ("/checkout_order")
    public ResponseEntity<ApiResponse<Order>> checkoutOrder(@RequestBody OrderInput postData){
        ApiResponse<Order> response = new ApiResponse<>();
        try {
            Order order = orderService.checkout_order(postData);

            response.setData(order);
            response.setMessage("Order registered successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Order>> registerOrder(@RequestBody Order postData){
        ApiResponse<Order> response = new ApiResponse<>();
        try {
            Order order = orderService.registerOrder(postData);

            // next update this part
            order.setBillingAddress(null);
            order.setStatus(null);
            order.setCreatedDate(null);
            order.setUpdatedDate(null);

            response.setData(order);
            response.setMessage("Order registered successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderId(@PathVariable int orderId) {
        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order order = orderService.findById(orderId);

            response.setData(order);
            response.setMessage("Get order by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
