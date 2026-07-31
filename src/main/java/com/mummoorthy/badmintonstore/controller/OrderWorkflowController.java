package com.mummoorthy.badmintonstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.dto.OrderResponse;
import com.mummoorthy.badmintonstore.dto.PlaceOrderRequest;
import com.mummoorthy.badmintonstore.service.OrderWorkflowService;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class OrderWorkflowController {

    @Autowired
    private OrderWorkflowService orderWorkflowService;

    @PostMapping("/place-order")
    public OrderResponse placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return orderWorkflowService.placeOrder(request);

    }

}