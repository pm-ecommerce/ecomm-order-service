package com.pm.ecommerce.order_service.model;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.enums.OrderItemStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ScheduledDeliveryResponse {

    int id;
    OrderItemStatus status;
    private DeliveryAddress address;
    private Timestamp deliveryDate;
    private Timestamp deliveredDate;
    private List<OrderItem> items;
    private VendorResponse vendor;
    private UserResponse user;

    public ScheduledDeliveryResponse(ScheduledDelivery scheduledDelivery) {
        id = scheduledDelivery.getId();
        status = scheduledDelivery.getStatus();
        address = scheduledDelivery.getAddress();
        deliveryDate = scheduledDelivery.getDeliveryDate();
        deliveredDate = scheduledDelivery.getDeliveredDate();
        items = scheduledDelivery.getItems();
        vendor = new VendorResponse(scheduledDelivery.getVendor().getId(), scheduledDelivery.getVendor().getBusinessName());
        user = new UserResponse(scheduledDelivery.getUser().getId(), scheduledDelivery.getUser().getName());
    }

    class VendorResponse {
        int id;
        String name;

        public VendorResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    class UserResponse {
        int id;
        String name;

        public UserResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
