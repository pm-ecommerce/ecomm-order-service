package com.pm.ecommerce.order_service.notifications.event_data;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.ScheduledDelivery;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EmailScheduledDelivery {
    int id;

    String userName;
    String vendorName;

    String address1;
    String address2;
    String city;
    String zipcode;
    String state;
    String country;

    List<OrderItemEmail> orderItems;

    public EmailScheduledDelivery(ScheduledDelivery delivery) {
        id = delivery.getId();
        vendorName = delivery.getVendor().getName();
        userName = delivery.getUser().getName();
        orderItems = delivery.getItems().stream().map(OrderItemEmail::new).collect(Collectors.toList());
        Address address = delivery.getAddress();
        address1 = address.getAddress1();
        address2 = address.getAddress2();
        city = address.getCity();
        state = address.getState();
        zipcode = address.getZipcode();
        country = "USA";
    }
}
