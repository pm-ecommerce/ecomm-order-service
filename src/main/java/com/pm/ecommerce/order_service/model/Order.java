package com.pm.ecommerce.order_service.model;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.OrderItem;
import com.pm.ecommerce.entities.Transaction;
import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(targetEntity = User.class,
            cascade = {CascadeType.DETACH},
            fetch = FetchType.LAZY)

    private User user;
    @OneToOne(targetEntity = Address.class,
            cascade = {CascadeType.DETACH})
//    @NotNull
    private Address billingAddress;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.DETACH})
//    @NotNull
    private Address shippingAddress;

    @Column(columnDefinition = "double default 0.00")
    private double tax;

    private OrderStatus status;
    @OneToMany(targetEntity = Transaction.class,
            cascade = {CascadeType.DETACH},
            fetch = FetchType.LAZY)
//    @NotNull
    private List<Transaction> transactions;

    @OneToMany(targetEntity = OrderItem.class,
            cascade = {CascadeType.DETACH},
            fetch = FetchType.LAZY)
//    @NotNull
    private List<OrderItem> items;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdDate;

    private Timestamp updatedDate;

    public Order() {
    }


}

