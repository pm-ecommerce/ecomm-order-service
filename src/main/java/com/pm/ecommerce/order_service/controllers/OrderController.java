package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.order_service.model.*;
import com.pm.ecommerce.order_service.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<OrderResponse>> checkoutOrder(@RequestBody OrderInput postData) {
        ApiResponse<OrderResponse> response = new ApiResponse<>();
        try {
            OrderResponse order = orderService.checkoutOrder(postData);

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

    @GetMapping("users/{userId}/active")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getUsersOrders(@PathVariable int userId,
                                                                                                @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getUserOrders(userId, page, itemsPerPage, true);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("users/{userId}/complete")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getUsersCompleteOrders(@PathVariable int userId,
                                                                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                        @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getUserOrders(userId, page, itemsPerPage, false);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("vendors/{vendorId}/active")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getVendorsOrders(@PathVariable int vendorId,
                                                                                                  @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                  @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getVendorOrders(vendorId, page, itemsPerPage, true);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("vendors/{vendorId}/complete")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getVendorsCompleteOrders(@PathVariable int vendorId,
                                                                                                          @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                          @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getVendorOrders(vendorId, page, itemsPerPage, false);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{sessionId}/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItemResponse>> deleteCartItem(@PathVariable String sessionId, @PathVariable int cartItemId) {
        ApiResponse<CartItemResponse> response = new ApiResponse<>();
        try {
            CartItemResponse carItemResponse = orderService.deleteCartItem(cartItemId, sessionId);
            response.setData(carItemResponse);
            response.setMessage("Cart Item deleted!");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    /// ==== Today's task
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getActiveOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                 @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getActiveOrders(page, itemsPerPage, true);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getCompletedOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                    @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getActiveOrders(page, itemsPerPage, false);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/updateStatus/{deliveryId}/{status}")
    public ResponseEntity<ApiResponse<ScheduledDeliveryResponse>> getUpdatedOrderStatus(@PathVariable int deliveryId, @PathVariable int status) {
        ApiResponse<ScheduledDeliveryResponse> response = new ApiResponse<>();

        try {
            ScheduledDeliveryResponse scheduledDelivery = orderService.updateOrderStatus(deliveryId, status);
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
