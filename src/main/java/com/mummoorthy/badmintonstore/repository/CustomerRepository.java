package com.mummoorthy.badmintonstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirebaseUid(String firebaseUid);

    Optional<Customer> findByEmail(String email);

}