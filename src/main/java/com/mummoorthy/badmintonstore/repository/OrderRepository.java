package com.mummoorthy.badmintonstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);
    
    List<Order> findByStatus(String status);
    
    
}