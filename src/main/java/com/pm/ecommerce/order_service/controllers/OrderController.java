package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Order>> checkoutOrder(@RequestBody OrderInput postData) {
        ApiResponse<Order> response = new ApiResponse<>();
        try {
            Order order = orderService.checkoutOrder(postData);

            response.setData(order);
            response.setMessage("Order registered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    //dont return order, return scheduled deliveries
    @GetMapping("users/{userId}")
    public List<Order> getAllUserOrders() {
        return null;
//        return orderService.getAllOrders();
    }

    @GetMapping("vendors/{userId}")
    //dont return order, return scheduled deliveries
    public List<Order> getAllVendorsOrders() {
        return null;
//        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderId(@PathVariable int orderId) {
        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order order = null;
//            Order order = orderService.findById(orderId);

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
