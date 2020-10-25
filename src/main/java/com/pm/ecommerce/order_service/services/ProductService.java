package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.order_service.repositories.CategoryRepository;
import com.pm.ecommerce.order_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductsByCategory(int productId) {
        return productRepository.findById(productId);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Product getProductById(int productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(() -> new Exception("Product is not found"));
    }
}
