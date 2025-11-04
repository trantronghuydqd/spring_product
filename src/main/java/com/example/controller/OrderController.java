package com.example.controller;

import com.example.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/checkout")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String checkout() {
        return "orders/checkout";
    }
    
    @PostMapping
    public String processCheckout(Authentication auth, HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            orderService.createOrder(auth.getName(), cart);
            session.removeAttribute("cart");
        }
        return "redirect:/orders/success";
    }
}
