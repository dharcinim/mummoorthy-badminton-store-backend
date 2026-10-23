package com.mummoorthy.badmintonstore.mapper;

import org.springframework.stereotype.Component;

import com.mummoorthy.badmintonstore.dto.OrderItemDTO;
import com.mummoorthy.badmintonstore.dto.OrderResponse;
import com.mummoorthy.badmintonstore.entity.Order;
import com.mummoorthy.badmintonstore.entity.OrderItem;
import com.mummoorthy.badmintonstore.dto.ShippingAddressDTO;
import com.mummoorthy.badmintonstore.entity.Address;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setStatus(order.getStatus());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());

        if (order.getCustomer() != null) {
            response.setCustomer(order.getCustomer());
        }
        
        if (order.getShippingAddress() != null) {
        	ShippingAddressDTO dto = new ShippingAddressDTO();

        	Address address = order.getShippingAddress();

        	dto.setFullName(address.getFullName());
        	dto.setPhoneNumber(address.getPhoneNumber());
        	dto.setAddressLine1(address.getAddressLine1());
        	dto.setAddressLine2(address.getAddressLine2());
        	dto.setCity(address.getCity());
        	dto.setState(address.getState());
        	dto.setCountry(address.getCountry());
        	dto.setPostalCode(address.getPostalCode());

        	response.setShippingAddress(dto);
        }

        if (order.getOrderItems() != null) {

            response.setOrderItems(
                    order.getOrderItems()
                            .stream()
                            .map(this::toItemDTO)
                            .toList()
            );
        }

        return response;
    }

    public OrderItemDTO toItemDTO(OrderItem item) {

        OrderItemDTO dto = new OrderItemDTO();

        dto.setId(item.getId());
        dto.setProductId(
                item.getProductVariant()
                        .getProduct()
                        .getId()
        );

        dto.setProductVariantId(
                item.getProductVariant()
                        .getId()
        );
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());

        dto.setProductName(
                item.getProductVariant()
                        .getProduct()
                        .getName()
        );
        dto.setBrandName(
                item.getProductVariant()
                        .getProduct()
                        .getBrand()
                        .getName()
        );
        
        dto.setWeight(
                item.getProductVariant()
                        .getWeight()
        );

        dto.setGripSize(
                item.getProductVariant()
                        .getGripSize()
        );

        if (!item.getProductVariant()
                .getProduct()
                .getProductImages()
                .isEmpty()) {

            dto.setProductImageUrl(
                    item.getProductVariant()
                            .getProduct()
                            .getProductImages()
                            .get(0)
                            .getImageUrl()
            );
        }

        return dto;
    }
}