package com.mummoorthy.badmintonstore.dto;
import com.mummoorthy.badmintonstore.entity.Customer;



import java.time.LocalDateTime;
import java.util.List;
import com.mummoorthy.badmintonstore.entity.Address;
public class OrderResponse {

    private Long orderId;
    private String orderNumber;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private Customer customer;
    private ShippingAddressDTO shippingAddress;
    
    
    
    
	public ShippingAddressDTO getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	private List<OrderItemDTO> orderItems;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

    // Getters & Setters
}