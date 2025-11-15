package com.example.toyProject.controller;

import com.example.toyProject.DTO.Order;
import com.example.toyProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder")
    public int[] createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    @GetMapping("getOrder")
    public Order getOrder(@RequestParam("id") Integer id) {
        return orderService.getOrderBasedOnId(id);
    }
}
