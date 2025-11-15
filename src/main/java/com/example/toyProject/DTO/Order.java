package com.example.toyProject.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class Order {
    private Integer id;
    private String username;
    private LocalDate placedAt;
    private Double totalAmount;
    private String address;
    private LocalDate deliveryDate;
    private Map<Integer, Integer> productIdToQuantity;
    private List<Product> products;
}