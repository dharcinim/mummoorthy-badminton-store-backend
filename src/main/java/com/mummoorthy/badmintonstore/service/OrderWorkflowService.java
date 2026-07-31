package com.mummoorthy.badmintonstore.service;

import com.mummoorthy.badmintonstore.dto.OrderResponse;
import com.mummoorthy.badmintonstore.dto.PlaceOrderRequest;

public interface OrderWorkflowService {

    OrderResponse placeOrder(PlaceOrderRequest request);

}