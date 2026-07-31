package com.mummoorthy.badmintonstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.dto.OrderResponse;
import com.mummoorthy.badmintonstore.dto.PlaceOrderRequest;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.Cart;
import com.mummoorthy.badmintonstore.entity.CartItem;
import com.mummoorthy.badmintonstore.entity.Customer;
import com.mummoorthy.badmintonstore.entity.Address;
import com.mummoorthy.badmintonstore.entity.Order;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.entity.OrderItem;


import com.mummoorthy.badmintonstore.repository.AddressRepository;
import com.mummoorthy.badmintonstore.repository.CartItemRepository;
import com.mummoorthy.badmintonstore.repository.CartRepository;
import com.mummoorthy.badmintonstore.repository.CustomerRepository;
import com.mummoorthy.badmintonstore.repository.OrderItemRepository;
import com.mummoorthy.badmintonstore.repository.OrderRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

@Service
public class OrderWorkflowServiceImpl implements OrderWorkflowService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        // ==============================
        // STEP 1 - Validate Customer
        // ==============================

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        // ==============================
        // STEP 2 - Get Customer Cart
        // ==============================

        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        // ==============================
        // STEP 3 - Get Cart Items
        // ==============================

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {

            throw new RuntimeException("Your cart is empty.");

        }

     // ==============================
     // STEP 4 - Save Shipping Address
     // ==============================

     Address address = new Address();

     address.setCustomer(customer);
     address.setFullName(request.getFullName());
     address.setPhoneNumber(request.getPhoneNumber());
     address.setAddressLine1(request.getAddressLine1());
     address.setAddressLine2(request.getAddressLine2());
     address.setCity(request.getCity());
     address.setState(request.getState());
     address.setCountry(request.getCountry());
     address.setPostalCode(request.getPostalCode());
     address.setDefaultAddress(true);

     addressRepository.save(address);
     
  // ==============================
  // STEP 5 - Create Order
  // ==============================

  Order order = new Order();

  order.setCustomer(customer);

  order.setOrderNumber(
          "MMB-" + System.currentTimeMillis());

  order.setStatus("PENDING");

  order.setTotalAmount(0.0);

  order = orderRepository.save(order);
  
//==============================
//STEP 6 - Create Order Items
//==============================

double totalAmount = 0;

for (CartItem cartItem : cartItems) {

   ProductVariant variant = cartItem.getProductVariant();

   // Check stock
   if (variant.getStock() < cartItem.getQuantity()) {

       throw new RuntimeException(
               variant.getProduct().getName()
               + " is out of stock.");
   }

   OrderItem orderItem = new OrderItem();

   orderItem.setOrder(order);

   orderItem.setProductVariant(variant);

   orderItem.setQuantity(cartItem.getQuantity());

   orderItem.setPrice(variant.getSellingPrice());

   orderItemRepository.save(orderItem);

   // Calculate Total
   totalAmount +=
           variant.getSellingPrice()
           * cartItem.getQuantity();

   // Reduce Stock
   variant.setStock(
           variant.getStock()
           - cartItem.getQuantity());

   productVariantRepository.save(variant);

}
//==============================
//STEP 7 - Update Total Amount
//==============================

order.setTotalAmount(totalAmount);

orderRepository.save(order);

//==============================
//STEP 8 - Clear Cart
//==============================

cartItemRepository.deleteAll(cartItems);

OrderResponse response = new OrderResponse();

response.setOrderId(order.getId());

response.setOrderNumber(order.getOrderNumber());

response.setStatus(order.getStatus());

response.setTotalAmount(order.getTotalAmount());

return response;
    }

}