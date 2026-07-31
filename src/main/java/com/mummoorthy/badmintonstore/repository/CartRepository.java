package com.mummoorthy.badmintonstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerId(Long customerId);

}