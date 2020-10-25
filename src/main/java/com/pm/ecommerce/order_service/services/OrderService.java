package com.pm.ecommerce.order_service.services;

//import com.pm.ecommerce.order_service.entities.Order;
import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.controllers.OrderInput;
import com.pm.ecommerce.order_service.interfaces.*;
import com.pm.ecommerce.order_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.pm.ecommerce.enums.OrderStatus.RECEIVED;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

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

    //
    //1. validation
    //2. create an order
    //3. send an email

    //shipping address, billgin address. shippingAddressId, BillingAddressId
    @RequestMapping("checkout_order")
    public ResponseEntity<ApiResponse<Order>> checkout_order(@RequestBody OrderInput orderInput){
        Cart addCartRequest = orderInput.getAddCartRequest();
        Integer shippingAddressId = orderInput.getShippingAddressId();
        ApiResponse<Order> response = new ApiResponse<>();
        Order order = new Order();
        Address billingAddress = addressService.findById(orderInput.getBillingAddressId());
        Address  shippingAddress = addressService.findById(shippingAddressId);
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setStatus(RECEIVED);
        //calcuateTax
        Double tax = 1.0;
        order.setTax(tax);
        order.setUser(addCartRequest.getUser());
        String userDeliveryEmail = addCartRequest.getUser().getEmail();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem item: addCartRequest.getCartItems()) {
            OrderItem orderItem =  new OrderItem();
            orderItem.setProduct(item.getProduct());
            Vendor vendor = item.getProduct().getVendor();
            List<OrderItemAttribute> orderItemAttributeArrayList = new ArrayList<>();
        /*    for (CartItemAttribute cartItemAttributeList: item.getAttributes()) {
                OrderItemAttribute orderItemAttribute = new OrderItemAttribute();
                orderItemAttribute.setOption(cartItemAttributeList.getOption());
                orderItemAttributeArrayList.add(orderItemAttribute);
            }*/
            orderItem.setAttributes(orderItemAttributeArrayList);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setRate(item.getRate());

            //difference
            //set OrderId, pass deliverDate,
            scheduleDeliver(vendor, new Timestamp(orderInput.getDeliveryDate()), shippingAddressId);
            orderItemList.add(orderItem);
        }
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

    }
    public int getOrderId() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    //
    public Order registerOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(int orderId) {
        Optional<Order> result = orderRepository.findById(orderId);

        Order order = null;
        if (result.isPresent()) {
            order = result.get();
        } else {
            throw new RuntimeException("Did not find order id - " + orderId);
        }
        return order;
    }




}
