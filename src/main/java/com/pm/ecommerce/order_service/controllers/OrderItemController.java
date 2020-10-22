package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.order_service.interfaces.IOrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {

    @Autowired
    private IOrderItemsService orderItemsService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<OrderItem>> registerOrderItem(@RequestBody OrderItem postData){
        ApiResponse<OrderItem> response = new ApiResponse<>();
        try {
            OrderItem orderItem = orderItemsService.registerOrder(postData);

            // next update this part
            orderItem.setProduct(null);
            orderItem.setAttributes(null);

            response.setData(orderItem);
            response.setMessage("Order item registered successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_orderItems")
    public List<OrderItem> getAllOrderItems() {
        return orderItemsService.getAllOrderItems();
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<ApiResponse<OrderItem>> getOrderItemId(@PathVariable int orderItemId) {
        ApiResponse<OrderItem> response = new ApiResponse<>();

        try {
            OrderItem orderItem = orderItemsService.findById(orderItemId);

            // next update this part
            orderItem.setProduct(null);
            orderItem.setAttributes(null);

            response.setData(orderItem);
            response.setMessage("Get order item by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
























}
