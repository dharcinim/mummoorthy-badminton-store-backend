package com.mummoorthy.badmintonstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}