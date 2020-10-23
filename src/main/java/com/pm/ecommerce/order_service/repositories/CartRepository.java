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

    // addCart by product with user id
    // remove cart by userid and cartid
    // getCartByuserId

//    @Query("SELECT cart from Cart cart WHERE cart.id =: user.id.account.id.productId")
//    List<Cart> getCartByuserId();

//    @Query("Select addCart FROM AddtoCart addCart WHERE addCart.user_id=:user_id")
//    List<AddtoCart> getCartByuserId(@Param("user_id")Long user_id);



}
