package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.Address;

public interface AddressService {

    List<Address> getAllAddresses();

    Address getAddressById(Long id);

    Address saveAddress(Address address);

    Address updateAddress(Long id, Address address);

    void deleteAddress(Long id);
}