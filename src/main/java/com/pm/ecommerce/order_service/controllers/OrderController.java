package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

import static com.pm.ecommerce.enums.OrderStatus.RECEIVED;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;
//    @Autowired
//    private IAddressService addressService;
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IVendorService vendorService;
//    @Autowired
//    private IEmailService emailService;

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

            // next update this part
            order.setBillingAddress(null);
            order.setStatus(null);
            order.setCreatedDate(null);
            order.setUpdatedDate(null);

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
