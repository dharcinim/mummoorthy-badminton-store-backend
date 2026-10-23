package com.mummoorthy.badmintonstore.controller;
import com.mummoorthy.badmintonstore.dto.OrderResponse;
import java.util.List;
import com.mummoorthy.badmintonstore.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.dto.UpdateOrderStatusRequest;
import com.mummoorthy.badmintonstore.entity.Order;
import com.mummoorthy.badmintonstore.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getOrdersByCustomer(
            @PathVariable Long customerId) {

        return orderService.getOrdersByCustomer(customerId);
    }
    
    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id,
                             @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }
    
    @PutMapping("/{id}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {

        Order order = orderService.updateOrderStatus(id, request.getStatus());

        return orderMapper.toResponse(order);
    }

    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
    
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully";
    }
}