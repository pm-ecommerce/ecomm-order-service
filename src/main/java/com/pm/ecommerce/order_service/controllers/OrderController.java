package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.*;
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

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.pm.ecommerce.enums.OrderStatus.RECEIVED;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private IEmailService emailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ScheduledDeliveryRepository scheduledDeliveryRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    //1. validation root
    //2. create an order
    //3. send an email

    //shipping address, billing address. shippingAddressId, BillingAddressId
    @RequestMapping("checkout_order")
    public ResponseEntity<ApiResponse<Order>> checkout_order(@RequestBody OrderInput orderInput){
        Cart cart = cartRepository.findById(orderInput.getId()).orElse(null);

        Integer shippingAddressId = orderInput.getShippingAddressId();

        ApiResponse<Order> response = new ApiResponse<>();
        Order order = new Order();
        Address billingAddress = addressService.findById(orderInput.getBillingAddressId());
        Address  shippingAddress = addressService.findById(shippingAddressId);

        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);

        User user = userService.findById(cart.getUser().getId());
        order.setUser(user);
        order.setStatus(RECEIVED);

        //calcuateTax
        Double tax = 1.0;
        order.setTax(tax);

//        String userDeliveryEmail = cart.getUser().getEmail();
        String userDeliveryEmail = user.getEmail();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem item: cart.getCartItems()) {
            OrderItem orderItem =  new OrderItem();
            orderItem.setProduct(item.getProduct());

            Vendor vendor = item.getProduct().getVendor();
            List<OrderItemAttribute> orderItemAttributeArrayList = item.getAttributes().stream()
                    .map(e->{
                        OrderItemAttribute attribute = new OrderItemAttribute();
                        attribute.setOption(e.getOption());
                        attribute.setName(e.getName());
                        return attribute;
                    }).collect(Collectors.toList());
            orderItem.setAttributes(orderItemAttributeArrayList);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setRate(item.getRate());
            orderItemRepository.save(orderItem);
            order.setItems(Arrays.asList(orderItem));
            scheduleDeliver(vendor, new Timestamp(orderInput.getDeliveryDate()), shippingAddressId);
            orderItemList.add(orderItem);
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(1);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transaction);
        order.setTransactions(Arrays.asList(transaction));

        order.setItems(orderItemList);
        orderService.registerOrder(order);
        response.setData(order);
        response.setMessage("Order registered successfully.");
        emailService.sendNotification(userDeliveryEmail, "Order successfully placed");
        return ResponseEntity.ok(response);
    }


    private void scheduleDeliver(Vendor vendor, Timestamp deliveryDate, Integer deliveryAddressId){
        ScheduledDelivery scheduledDelivery = new ScheduledDelivery();
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        Address address = addressService.findById(deliveryAddressId);
        deliveryAddress.setAddress1(address.getAddress1());
        deliveryAddress.setCity(address.getCity());
        deliveryAddress.setState(address.getState());
        deliveryAddress.setZipcode(address.getZipcode());
        deliveryAddress.setCountry(address.getCountry());
        //set receiver, phone and email,
        scheduledDelivery.setAddress(deliveryAddress);
        scheduledDelivery.setDeliveryDate(deliveryDate);
        scheduledDelivery.setVendor(vendor);
        scheduledDeliveryRepository.save(scheduledDelivery);

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
