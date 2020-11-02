package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.order_service.model.CartItemResponse;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.model.PagedResponse;
import com.pm.ecommerce.order_service.model.ScheduledDeliveryResponse;
import com.pm.ecommerce.order_service.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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
    @GetMapping("users/{userId}/active")
    public ResponseEntity<ApiResponse<PagedResponse<ScheduledDeliveryResponse>>> getUsersOrders(@PathVariable int userId,
                                                                               @RequestParam(name = "page", defaultValue = "1") int page,
                                                                               @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<ScheduledDeliveryResponse>> response = new ApiResponse<>();

        try {
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getUserOrders(userId, page, itemsPerPage, true) ;
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
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getUserOrders(userId, page, itemsPerPage, false) ;
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
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getVendorOrders(vendorId, page, itemsPerPage, true) ;
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
            PagedResponse<ScheduledDeliveryResponse> scheduledDelivery = orderService.getVendorOrders(vendorId, page, itemsPerPage, false) ;
            response.setData(scheduledDelivery);
            response.setMessage("Api to get orders successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
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


    @DeleteMapping("{sessionId}/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItemResponse>> deleteCartItem(@PathVariable String sessionId, @PathVariable int cartItemId){
        ApiResponse<CartItemResponse> response = new ApiResponse<>();
        try{
            CartItemResponse carItemResponse = orderService.deleteCartItem(cartItemId, sessionId);
            response.setData(carItemResponse);
            response.setMessage("Cart Item deleted!");
        } catch(Exception e){
            response.setStatus(500);
            response.setMessage(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
