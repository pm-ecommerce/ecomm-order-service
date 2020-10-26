package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.repositories.*;
import com.pm.ecommerce.order_service.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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


    public Order checkout_order(OrderInput orderInput){
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
//        orderService.registerOrder(order);
        response.setData(order);
        response.setMessage("Order registered successfully.");
        emailService.sendNotification(userDeliveryEmail, "Order successfully placed");
//        return ResponseEntity.ok(response);
        return orderService.registerOrder(order);
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

    public int getOrderId() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    public Order registerOrder(Order order) {
        if (order!=null) {
            order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            order.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }
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
