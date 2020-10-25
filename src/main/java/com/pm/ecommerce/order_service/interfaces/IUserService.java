package com.pm.ecommerce.order_service.interfaces;

import com.pm.ecommerce.entities.User;

public interface IUserService {
    public User findById(int userId);
}
