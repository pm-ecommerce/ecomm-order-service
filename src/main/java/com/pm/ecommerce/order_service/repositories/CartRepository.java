package com.pm.ecommerce.order_service.repositories;

import com.pm.ecommerce.entities.Cart;
import com.pm.ecommerce.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // itemCart by product with

    // addCart by product with user id
    // remove cart by userid and cartid
    // getCartByuserId

//    @Query("Select addCart  FROM Cart addCart WHERE addCart.user =: userIid")
//    List<Cart> getCartByuserId(@Param("userId")Integer userId);

//    @Query("Select addCart  FROM Cart addCart WHERE addCart.product.id= :product_id and addCart.user_id=:user_id")
//    Optional<AddtoCart> getCartByProductIdAnduserId(@Param("user_id")Long user_id,@Param("product_id")Long product_id);

}
