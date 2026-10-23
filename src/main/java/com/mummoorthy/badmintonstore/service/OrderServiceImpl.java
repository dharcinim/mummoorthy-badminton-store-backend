package com.mummoorthy.badmintonstore.service;

import com.mummoorthy.badmintonstore.mapper.OrderMapper;
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
import com.mummoorthy.badmintonstore.entity.OrderItem;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import org.springframework.transaction.annotation.Transactional;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

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

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Transactional
	@Override
	public OrderResponse placeOrder(PlaceOrderRequest request) {

		Customer customer = customerRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Cart cart = cartRepository.findByCustomerId(customer.getId())
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
		System.out.println("=================================");
		System.out.println("Cart Size: " + cartItems.size());
		System.out.println("=================================");

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

			System.out.println("Saving Order Item...");
			System.out.println("Variant ID: " + item.getProductVariant().getId());
			System.out.println("Quantity: " + item.getQuantity());

			OrderItem orderItem = new OrderItem();

			orderItem.setOrder(order);
			orderItem.setProductVariant(item.getProductVariant());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getProductVariant().getSellingPrice());

			orderItemRepository.save(orderItem);

			System.out.println("Saved Successfully");

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

		System.out.println("========== UPDATE ORDER STATUS ==========");
		System.out.println("Order ID : " + orderId);
		System.out.println("Status received : " + status);

		Order order = orderRepository.findById(orderId).orElse(null);

		if (order == null) {
			System.out.println("Order not found");
			return null;
		}

		System.out.println("Status before update : " + order.getStatus());
		
		System.out.println("Order Items Count = " + order.getOrderItems().size());

		// Restore stock only once when cancelling
		if ("CANCELLED".equalsIgnoreCase(status) && !"CANCELLED".equalsIgnoreCase(order.getStatus())) {

			for (OrderItem item : order.getOrderItems()) {

				ProductVariant variant = item.getProductVariant();

				variant.setStock(variant.getStock() + item.getQuantity());

				productVariantRepository.save(variant);

				System.out.println(
						"Restored stock for Variant ID " + variant.getId() + " | New Stock = " + variant.getStock());
			}
		}

		order.setStatus(status);

		System.out.println("Status after update : " + order.getStatus());

		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public OrderResponse getOrderById(Long id) {

		Order order = orderRepository.findById(id).orElse(null);

		if (order == null) {
			return null;
		}

		return orderMapper.toResponse(order);
	}

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<OrderResponse> getOrdersByCustomer(Long customerId) {

		return orderRepository.findByCustomerId(customerId).stream().map(orderMapper::toResponse).toList();

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
	public Order cancelOrder(Long id) {

		Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
			throw new RuntimeException("Only pending orders can be cancelled.");
		}

		order.setStatus("CANCELLED");

		return orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}
}