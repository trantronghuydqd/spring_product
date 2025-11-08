package com.example.controller;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
public class RegisterController {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        // Check if username exists
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            model.addAttribute("error", "Username đã tồn tại!");
            return "register";
        }
        
        // Encode password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole("CUSTOMER");
        customer.setCustomerSince(new Date());
        
        customerRepository.save(customer);
        
        return "redirect:/login?registered";
    }
}
