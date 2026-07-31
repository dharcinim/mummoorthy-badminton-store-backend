package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.OrderItem;
import com.mummoorthy.badmintonstore.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {

        OrderItem existingItem = orderItemRepository.findById(id).orElse(null);

        if (existingItem != null) {

            existingItem.setOrder(orderItem.getOrder());
            existingItem.setProductVariant(orderItem.getProductVariant());
            existingItem.setQuantity(orderItem.getQuantity());
            existingItem.setPrice(orderItem.getPrice());

            return orderItemRepository.save(existingItem);
        }

        return null;
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}