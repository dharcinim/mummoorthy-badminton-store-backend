package com.mummoorthy.badmintonstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.Customer;
import com.mummoorthy.badmintonstore.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    
    @GetMapping("/firebase/{uid}")
    public Customer getCustomerByFirebaseUid(@PathVariable String uid) {
        return customerService.getCustomerByFirebaseUid(uid);
    }

    
    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {

        System.out.println("=================================");
        System.out.println("Firebase UID : " + customer.getFirebaseUid());
        System.out.println("Full Name    : " + customer.getFullName());
        System.out.println("Email        : " + customer.getEmail());
        System.out.println("=================================");

        return customerService.saveCustomer(customer);
    }
   /* @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }*/
   

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id,
                                   @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
}