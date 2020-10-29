package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.order_service.model.Charge;
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
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ICartItemService cartItemService;


    public Order checkout_order(OrderInput orderInput) throws Exception {

        Cart cart = cartRepository.findById(orderInput.getId()).orElse(null);
        if(cart == null) throw new Exception("Cart Not Found");

        ApiResponse<Order> response = new ApiResponse<>();

        Order order = new Order();
        Address billingAddress = addressService.findById(orderInput.getBillingAddressId());
        Address shippingAddress = addressService.findById(orderInput.getShippingAddressId());

        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);

        User user = userService.findById(cart.getUser().getId());
        order.setUser(user);
        order.setStatus(RECEIVED);

        double tax = 1.0;
        order.setTax(tax);

        String userDeliveryEmail = user.getEmail();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            Vendor vendor = cartItem.getProduct().getVendor();
            List<OrderItemAttribute> orderItemAttributeArrayList = cartItem.getAttributes().stream()
                    .map(e -> {
                        OrderItemAttribute attribute = new OrderItemAttribute();
                        attribute.setOption(e.getOption());
                        attribute.setName(e.getName());
                        return attribute;
                    }).collect(Collectors.toList());
            orderItem.setAttributes(orderItemAttributeArrayList);
            orderItem.setQuantity(cartItem.getQuantity()); // ch2
            orderItem.setRate(cartItem.getRate());

            //order.setItems(Arrays.asList(orderItem));
            //scheduleDeliver(vendor, new Timestamp(orderInput.getDeliveryDate()), shippingAddress.getId()/*shippingAddressId*/);
            orderItemList.add(orderItem);

        }

        for(Charge charge : orderInput.getCharges()){
            int cardId = charge.getCardId();
            double amount = charge.getAmount();

        }

        response.setData(order);
        response.setMessage("Order registered successfully.");
        emailService.sendNotification(userDeliveryEmail, "Order successfully placed");
        return orderService.registerOrder(order);
    }

    @Override
    public Boolean checkTotalAmountCart(double totalAmount, int userId, OrderInput orderInput) {
        Optional<Cart> total_amount = cartRepository.findById(orderInput.getId());
        if (total_amount.equals(totalAmount)) {
            return true;
        }
        System.out.print("Error from request " + total_amount + " --db-- " + totalAmount);
        return false;
    }

    @Override
    public List<CartItem> saveProductsForCheckout(List<CartItem> cartItemList) throws Exception {
        try {
            int user_id = cartItemList.get(0).getId();
            if (cartItemList.size() > 0) {
                cartItemRepository.saveAll(cartItemList);

//                this.removeAllCartByUserId(user_id);
//                return this.getAllCheckoutByUserId(user_id);
            } else {
                throw new Exception("Should not be empty");
            }
        } catch (Exception e) {
            throw new Exception("Error while checkout " + e.getMessage());
        }
        return null;
    }

    private void scheduleDeliver(Vendor vendor, Timestamp deliveryDate, Integer deliveryAddressId) {
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

    public Order registerOrder(Order order) {
        if (order != null) {
            order.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            order.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }
        return orderRepository.save(order);
    }


    public int getOrderId() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
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
