package com.mummoorthy.badmintonstore.service;

import java.util.List;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.dto.OrderResponse;
import com.mummoorthy.badmintonstore.dto.PlaceOrderRequest;
import com.mummoorthy.badmintonstore.entity.Order;
import com.mummoorthy.badmintonstore.entity.OrderItem;
import com.mummoorthy.badmintonstore.entity.Customer;
import com.mummoorthy.badmintonstore.entity.Cart;
import com.mummoorthy.badmintonstore.entity.CartItem;
import com.mummoorthy.badmintonstore.repository.CartRepository;
import com.mummoorthy.badmintonstore.repository.CustomerRepository;
import com.mummoorthy.badmintonstore.repository.OrderRepository;
import com.mummoorthy.badmintonstore.repository.CartItemRepository;
import com.mummoorthy.badmintonstore.repository.OrderItemRepository;

import org.springframework.transaction.annotation.Transactional;



@Service
public class OrderServiceImpl implements OrderService {
	
	
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    @Transactional
    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setOrderNumber("ORD" + System.currentTimeMillis());

        double totalAmount = 0;

        for (CartItem item : cartItems) {

            double price = item.getProductVariant().getSellingPrice();

            totalAmount += price * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order);
        
        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProductVariant(item.getProductVariant());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProductVariant().getSellingPrice());

            orderItemRepository.save(orderItem);
        }
        cartItemRepository.deleteAll(cartItems);

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());

        return response;
    }

    
    
    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    
    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Order updateOrder(Long id, Order order) {

        Order existingOrder = orderRepository.findById(id).orElse(null);

        if (existingOrder != null) {

            existingOrder.setOrderNumber(order.getOrderNumber());
            existingOrder.setCustomer(order.getCustomer());
            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setTotalAmount(order.getTotalAmount());

            return orderRepository.save(existingOrder);
        }

        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}