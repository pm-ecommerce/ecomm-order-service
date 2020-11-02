package com.pm.ecommerce.order_service.model;

import com.pm.ecommerce.entities.DeliveryAddress;
import com.pm.ecommerce.entities.Image;
import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.enums.OrderItemStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ScheduledDeliveryResponse {

    int id;
    OrderItemStatus status;
    private DeliveryAddress address;
    private Timestamp deliveryDate;
    private Timestamp deliveredDate;
    private List<OrderItemResponse> items;
    private VendorResponse vendor;
    private UserResponse user;

    public ScheduledDeliveryResponse() {

    }

    public ScheduledDeliveryResponse(ScheduledDelivery scheduledDelivery) {
        id = scheduledDelivery.getId();
        status = scheduledDelivery.getStatus();
        address = scheduledDelivery.getAddress();
        deliveryDate = scheduledDelivery.getDeliveryDate();
        deliveredDate = scheduledDelivery.getDeliveredDate();
        if (scheduledDelivery.getItems() != null && scheduledDelivery.getItems().size() > 0) {
            items = scheduledDelivery.getItems().stream().map(OrderItemResponse::new).collect(Collectors.toList());
        }

        if (scheduledDelivery.getVendor() != null) {
            vendor = new VendorResponse(scheduledDelivery.getVendor().getId(), scheduledDelivery.getVendor().getBusinessName());
        }

        if (scheduledDelivery.getUser() != null) {
            user = new UserResponse(scheduledDelivery.getUser().getId(), scheduledDelivery.getUser().getName());
        }
    }

    @Data
    public class OrderItemResponse {
        int id;
        String name;
        double rate;
        int quantity;
        String image;

        public OrderItemResponse() {

        }

        public OrderItemResponse(OrderItem item) {
            id = item.getId();
            rate = item.getRate();
            quantity = item.getQuantity();
            if (item.getProduct() != null) {
                name = item.getProduct().getName();
                if (item.getProduct().getImages() != null && item.getProduct().getImages().size() > 0) {
                    List<Image> images = new ArrayList<>(item.getProduct().getImages());
                    image = images.get(0).getName();
                }
            }
        }
    }

    @Data
    public class VendorResponse {
        int id;
        String name;

        public VendorResponse() {

        }

        public VendorResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    public class UserResponse {
        int id;
        String name;

        public UserResponse() {

        }

        public UserResponse(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
