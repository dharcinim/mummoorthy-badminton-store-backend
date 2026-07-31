package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.Customer;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
    
    Customer getCustomerByFirebaseUid(String firebaseUid);

}