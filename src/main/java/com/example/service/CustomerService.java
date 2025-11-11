package com.example.service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<Customer> findAll() {
        return customerRepository.findByIsActiveTrue();
    }
    
    public List<Customer> findAllIncludingInactive() {
        return customerRepository.findAll();
    }
    
    public Customer findById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }
    
    public void save(Customer customer) {
        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }
        customerRepository.save(customer);
    }
    
    public void deleteById(Integer id) {
        // Soft delete: set isActive = false
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setIsActive(false);
            customerRepository.save(customer);
        }
    }
    
    public long count() {
        return customerRepository.count();
    }
}
