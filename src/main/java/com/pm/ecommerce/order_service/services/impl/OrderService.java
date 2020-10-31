package com.pm.ecommerce.order_service.services.impl;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.enums.OrderItemStatus;
import com.pm.ecommerce.enums.OrderStatus;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.enums.VendorStatus;
import com.pm.ecommerce.order_service.config.NewOrderEvent;
import com.pm.ecommerce.order_service.config.NewScheduledDeliveryEvent;
import com.pm.ecommerce.order_service.model.ChargeModel;
import com.pm.ecommerce.order_service.model.OrderInput;
import com.pm.ecommerce.order_service.repositories.*;
import com.pm.ecommerce.order_service.services.IAddressService;
import com.pm.ecommerce.order_service.services.IOrderService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Value("stripe.apiKey:sk_test_I8Ora3L8Af2oo9fgBykDOAxj")
    private String apiKey;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ScheduledDeliveryRepository scheduledDeliveryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AccountRepository accountRepository;

    public Order checkoutOrder(OrderInput orderInput) throws Exception {
        Cart cart = cartRepository.findBySessionId(orderInput.getSessionId()).orElse(null);
        if (cart == null) {
            throw new Exception("Cart Not Found");
        }

        User user = cart.getUser();

        // verify the cart
        Set<CartItem> cartItems = cart.getCartItems();

        // verify cart items
        for (CartItem item : cartItems) {
            verifyCartItem(item);
        }

        //verify cards
        for (ChargeModel charge : orderInput.getCharges()) {
            int cardId = charge.getCardId();
            Card card = cardRepository.findById(cardId).orElse(null);
            verifyCard(card, cart);
        }

        Address billingAddress = addressService.findById(orderInput.getBillingAddressId());

        // verify delivery address
        Address shippingAddress = addressService.findById(orderInput.getShippingAddressId());
        for (Address address : user.getAddresses()) {
            if (address.getId() != shippingAddress.getId()) {
                // TODO: uncomment after testing
                // throw new Exception("Address not found in your address book");
            }
        }

        // charge the cards
        List<Transaction> transactions = new LinkedList<>();
        for (ChargeModel charge : orderInput.getCharges()) {
            Transaction transaction = chargeCard(charge.getCardId(), charge.getAmount());
            transactions.add(transaction);
        }

        //validate if the total amount paid is equal to the required order total

        Order order = new Order();

        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setTransactions(transactions);
        order.setUser(user);
        order.setStatus(OrderStatus.RECEIVED);
        order.setTax(getTax(cart));

        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            List<OrderItemAttribute> orderItemAttributeArrayList = cartItem.getAttributes().stream()
                    .map(e -> {
                        OrderItemAttribute attribute = new OrderItemAttribute();
                        attribute.setOption(e.getOption());
                        attribute.setName(e.getName());
                        return attribute;
                    }).collect(Collectors.toList());
            orderItem.setAttributes(orderItemAttributeArrayList);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setRate(cartItem.getRate());
            orderItemList.add(orderItem);
        }
        order.setItems(orderItemList);
        order.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(order);

        //create a delivery address
        DeliveryAddress address = getDeliveryAddress(orderInput, shippingAddress, user);

        scheduleDeliver(address, order);

        // TODO: delete cart for the selected session id

        return order;
    }


    // TODO: API to get user orders; Scheduled deliveries
    public List<ScheduledDelivery> getUserOrders(int userId) {

        List<ScheduledDelivery> scheduledDeliveries = new ArrayList<>();

        Optional<Account> account = accountRepository.findById(userId);
        User user = new User();
        ScheduledDelivery scheduledDelivery = new ScheduledDelivery();
        OrderInput orderInput = new OrderInput();
        Order order = new Order();

        DeliveryAddress deliveryAddress = new DeliveryAddress();

//        scheduledDelivery.setId(order.getUser().getId());
        scheduledDelivery.setId(userId);
        scheduledDelivery.setItems(order.getItems());
        scheduledDelivery.setStatus(OrderItemStatus.DELIVERED);
        scheduledDelivery.setVendor(scheduledDelivery.getVendor());
        scheduledDelivery.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
        scheduledDelivery.setDeliveredDate(new Timestamp(System.currentTimeMillis()));
        scheduledDelivery.setAddress(deliveryAddress);
        scheduledDelivery.setVendor(scheduledDelivery.getVendor());

        scheduledDeliveries.add(scheduledDelivery);
        return scheduledDeliveries;
    }

    // TODO: API to get vendor orders; Scheduled deliveries
    public List<ScheduledDelivery> getVendorOrders(int vendorId) {

        List<ScheduledDelivery> scheduledDeliveries = new ArrayList<>();

        Optional<Account> account = accountRepository.findById(vendorId);
        User user = new User();
        ScheduledDelivery scheduledDelivery = new ScheduledDelivery();
        Vendor vendor = new Vendor();
        Order order = new Order();

        DeliveryAddress deliveryAddress = new DeliveryAddress();

        scheduledDelivery.setId(vendorId);
        scheduledDelivery.setItems(order.getItems());
        scheduledDelivery.setStatus(OrderItemStatus.DELIVERED);
        scheduledDelivery.setVendor(scheduledDelivery.getVendor());
        scheduledDelivery.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
        scheduledDelivery.setDeliveredDate(new Timestamp(System.currentTimeMillis()));
        scheduledDelivery.setAddress(deliveryAddress);
//        scheduledDelivery.setVendor(scheduledDelivery.getVendor());
        scheduledDelivery.setVendor(vendor);

        scheduledDeliveries.add(scheduledDelivery);
        return scheduledDeliveries;
    }

    private void scheduleDeliver(DeliveryAddress address, Order order) {
        // group order items by vendor
        Map<Integer, List<OrderItem>> map = new HashMap<>();
        Map<Integer, Vendor> vendorMap = new HashMap<>();
        for (OrderItem item : order.getItems()) {
            int vendorId = item.getProduct().getVendor().getId();
            if (!map.containsKey(vendorId)) {
                map.put(vendorId, new LinkedList<>());
            }

            if (!vendorMap.containsKey(vendorId)) {
                vendorMap.put(vendorId, item.getProduct().getVendor());
            }

            map.get(vendorId).add(item);
        }

        for (int vendorId : map.keySet()) {
            ScheduledDelivery delivery = new ScheduledDelivery();
            delivery.setAddress(address);
            delivery.setItems(map.get(vendorId));
            delivery.setVendor(vendorMap.get(vendorId));
            delivery.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
            delivery.setStatus(OrderItemStatus.RECEIVED);
            scheduledDeliveryRepository.save(delivery);
            //send an email to the vendor here
            publisher.publishEvent(new NewScheduledDeliveryEvent(this, delivery));
        }

        //send an email to the user
        publisher.publishEvent(new NewOrderEvent(this, order));
    }

    private DeliveryAddress getDeliveryAddress(OrderInput input, Address address, User user) {
        DeliveryAddress address1 = new DeliveryAddress();

        if (input.getReceiverEmail() == null) {
            address1.setEmail(user.getEmail());
        } else {
            address1.setEmail(input.getReceiverEmail());
        }

        if (input.getReceiverName() == null) {
            address1.setReceiver(user.getName());
        } else {
            address1.setReceiver(input.getReceiverName());
        }

        address1.setPhone(input.getReceiverPhone());
        address1.setAddress1(address.getAddress1());
        address1.setAddress2(address.getAddress2());
        address1.setCity(address.getCity());
        address1.setZipcode(address.getZipcode());
        address1.setState(address.getState());
        address1.setCountry(address.getCountry());

        return deliveryAddressRepository.save(address1);
    }

    private void verifyCartItem(CartItem item) throws Exception {
        if (item == null) {
            throw new Exception("Cart item is empty.");
        }

        Product product = item.getProduct();
        if (product == null || product.getStatus() != ProductStatus.PUBLISHED) {
            throw new Exception("Product not found");
        }

        Vendor vendor = product.getVendor();
        if (vendor == null || vendor.getStatus() != VendorStatus.APPROVED) {
            throw new Exception("Product vendor has not been approved yet.");
        }

        Category category = product.getCategory();
        if (category == null || category.isDeleted()) {
            throw new Exception("Category does not exist.");
        }
    }

    private void verifyCard(Card card, Cart cart) throws Exception {
        if (card == null) {
            throw new Exception("Card not found");
        }

        if (card.getUser().getId() != cart.getUser().getId()) {
            throw new Exception("Card ending with " + card.getLast4() + " does not belong to you.");
        }
    }

    private Transaction chargeCard(int cardId, double amount) throws Exception {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new Exception("Card not found");
        }
//        Stripe.apiKey = apiKey;
        Stripe.apiKey = "sk_test_I8Ora3L8Af2oo9fgBykDOAxj";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) amount * 100);
        params.put("currency", "usd");
        params.put("customer", card.getCustomerId());
        params.put("description", "My First Test Charge (created for API docs)");

        Charge charge = Charge.create(params);
        StripeTransaction transaction = new StripeTransaction();
        transaction.setCard(card);
        transaction.setAmount(amount);

        transaction.setChargeId(charge.getId());
        transactionRepository.save(transaction);

        return transaction;
    }

    private double getTax(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getQuantity() * item.getRate();
        }
        return (total * 7) / 100;
    }

}
