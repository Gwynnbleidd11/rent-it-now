package com.rentitnow.cart.repository;

import com.rentitnow.cart.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByCartId(Long cartId);
    List<Cart> findAll();
    @Override
    Cart save(Cart cars);
}
