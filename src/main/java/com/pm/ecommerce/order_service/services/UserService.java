package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.order_service.interfaces.IUserService;
import com.pm.ecommerce.order_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User findById(int userId)  {
        User user = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found."));
        } catch (Exception e) {
            System.out.println("");
        }
        return user;
    }
}
