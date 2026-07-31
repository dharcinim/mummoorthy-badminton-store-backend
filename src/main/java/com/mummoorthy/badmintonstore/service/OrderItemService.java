package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.OrderItem;

public interface OrderItemService {

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long id);

    OrderItem saveOrderItem(OrderItem orderItem);

    OrderItem updateOrderItem(Long id, OrderItem orderItem);

    void deleteOrderItem(Long id);
}