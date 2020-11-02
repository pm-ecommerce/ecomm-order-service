package com.pm.ecommerce.order_service.notifications.event_data;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.Order;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserOrderReceived {
    int id;
    String userName;
    String address1;
    String address2;
    String city;
    String zipcode;
    String state;
    String country;

    List<OrderItemEmail> orderItems;

    public UserOrderReceived(Order order) {
        id = order.getId();
        userName = order.getUser().getName();
        orderItems = order.getItems().stream().map(OrderItemEmail::new).collect(Collectors.toList());
        Address address = order.getShippingAddress();
        address1 = address.getAddress1();
        address2 = address.getAddress2();
        city = address.getCity();
        state = address.getState();
        zipcode = address.getZipcode();
    }
}
