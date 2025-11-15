package com.example.toyProject.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Product {
    private Integer id;
    private String name;
    private String category;
    private double price;
    private LocalDate createdAt;
}
