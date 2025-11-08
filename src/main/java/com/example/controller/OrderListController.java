package com.example.controller;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderListController {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @GetMapping("/success")
    public String orderSuccess() {
        return "orders/success";
    }
    
    @GetMapping
    public String myOrders(Authentication auth, Model model) {
        model.addAttribute("orders", orderService.findByCustomerUsername(auth.getName()));
        return "orders/list";
    }
    
    @GetMapping("/{id}/details")
    public String getOrderDetails(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        return "orders/details";
    }
}
