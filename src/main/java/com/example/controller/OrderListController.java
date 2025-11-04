package com.example.controller;

import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderListController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/success")
    public String orderSuccess() {
        return "orders/success";
    }
    
    @GetMapping
    public String myOrders(Authentication auth, Model model) {
        model.addAttribute("orders", orderService.findByCustomerUsername(auth.getName()));
        return "orders/list";
    }
}
