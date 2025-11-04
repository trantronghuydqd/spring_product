package com.example.service;

import com.example.model.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderLineRepository orderLineRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public Order createOrder(String username, Map<Integer, Integer> cart) {
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if (customer == null) return null;
        
        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(new Date());
        order = orderRepository.save(order);
        
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElse(null);
            if (product != null) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrder(order);
                orderLine.setProduct(product);
                orderLine.setAmount(entry.getValue());
                orderLine.setPurchasePrice(product.getPrice());
                orderLineRepository.save(orderLine);
            }
        }
        
        return order;
    }
    
    public List<Order> findByCustomerUsername(String username) {
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if (customer == null) return new ArrayList<>();
        List<Order> orders = orderRepository.findByCustomerId(customer.getId());
        
        // Load orderLines cho mỗi order
        for (Order order : orders) {
            List<OrderLine> orderLines = orderLineRepository.findByOrderId(order.getId());
            order.setOrderLines(orderLines);
        }
        
        return orders;
    }
}
