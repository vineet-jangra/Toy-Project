package com.example.toyProject.service;

import com.example.toyProject.DTO.Order;
import com.example.toyProject.DTO.Product;
import com.example.toyProject.repository.OrderRepository;
import com.example.toyProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public int[] createOrder(Order order) {
        Double totalAmount = calculateTotalAmount(order.getProductIdToQuantity());
        order.setTotalAmount(totalAmount);
        int orderId = orderRepository.insertOrder(order);
        return orderRepository.createMapping(orderId, order.getProductIdToQuantity());
    }

    private Double calculateTotalAmount(Map<Integer, Integer> productQuantityMap) {
        List<Product> products = productService.getAllProducts();
        double totalAmount = 0.0;
        for(Product product : products) {
            if(productQuantityMap.containsKey(product.getId())) {
                totalAmount += product.getPrice() * productQuantityMap.get(product.getId());
            }
        }
        return totalAmount;
    }

    public Order getOrderBasedOnId(Integer id) {
        Order order = orderRepository.getOrderBasedOnId(id);
        Map<Integer, Integer> idToQuantityMap = orderRepository.getProductIdToQuantityMapping(id);
        order.setProductIdToQuantity(idToQuantityMap);
        List<Product> allProducts = productService.getAllProducts();
        List<Product> orderProducts = new ArrayList<>();
        for(Product product : allProducts) {
            if(idToQuantityMap.containsKey(product.getId())) {
                orderProducts.add(product);
            }
        }
        order.setProducts(orderProducts);
        return order;
    }
}
