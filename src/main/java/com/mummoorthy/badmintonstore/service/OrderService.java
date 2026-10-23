package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.Order;
import com.mummoorthy.badmintonstore.dto.PlaceOrderRequest;
import com.mummoorthy.badmintonstore.dto.OrderResponse;

public interface OrderService {
	
	OrderResponse placeOrder(PlaceOrderRequest request);

    List<Order> getAllOrders();
    
    List<Order> getOrdersByStatus(String status);
    
    List<OrderResponse> getOrdersByCustomer(Long customerId);

    Order updateOrderStatus(Long orderId, String status);

    OrderResponse getOrderById(Long id);

    Order saveOrder(Order order);

    Order updateOrder(Long id, Order order);
    
    Order cancelOrder(Long id);

    void deleteOrder(Long id);
    
    
}