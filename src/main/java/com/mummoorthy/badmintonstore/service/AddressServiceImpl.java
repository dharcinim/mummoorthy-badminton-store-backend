package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.Address;
import com.mummoorthy.badmintonstore.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Address address) {

        Address existingAddress = addressRepository.findById(id).orElse(null);

        if (existingAddress != null) {

            existingAddress.setFullName(address.getFullName());
            existingAddress.setPhoneNumber(address.getPhoneNumber());
            existingAddress.setAddressLine1(address.getAddressLine1());
            existingAddress.setAddressLine2(address.getAddressLine2());
            existingAddress.setCity(address.getCity());
            existingAddress.setState(address.getState());
            existingAddress.setCountry(address.getCountry());
            existingAddress.setPostalCode(address.getPostalCode());
            existingAddress.setDefaultAddress(address.getDefaultAddress());
            existingAddress.setCustomer(address.getCustomer());

            return addressRepository.save(existingAddress);
        }

        return null;
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}