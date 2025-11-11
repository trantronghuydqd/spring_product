package com.example.controller;

import com.example.model.Product;
import com.example.model.Customer;
import com.example.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/products")
    public String adminProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }
    
    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }
    
    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute Product product, 
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product-form";
        }
        productService.save(product);
        return "redirect:/admin/products";
    }
    
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }
    
    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Integer id, 
                               @Valid @ModelAttribute Product product,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product-form";
        }
        product.setId(id);
        productService.save(product);
        return "redirect:/admin/products";
    }
    
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
    
    @GetMapping("/products/{id}/comments")
    public String viewProductComments(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("comments", commentService.findByProductId(id));
        return "admin/comments";
    }
    
    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Integer id, @RequestParam Integer productId) {
        commentService.deleteById(id);
        return "redirect:/admin/products/" + productId + "/comments";
    }
    
    // ========== QUẢN LÝ ĐỌN HÀNG ==========
    @GetMapping("/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("orderService", orderService); // để gọi calculateTotal trong template
        return "admin/orders";
    }
    
    @GetMapping("/orders/{id}")
    public String viewOrderDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("orderService", orderService); // để gọi calculateTotal trong template
        return "admin/order-detail";
    }
    
    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
        return "redirect:/admin/orders";
    }
    
    // ========== QUẢN LÝ KHÁCH HÀNG ==========
    @GetMapping("/customers")
    public String adminCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin/customers";
    }
    
    @GetMapping("/customers/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customer-form";
    }
    
    @PostMapping("/customers/add")
    public String addCustomer(@Valid @ModelAttribute Customer customer, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/customer-form";
        }
        // Hash password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCustomerSince(new java.util.Date());
        customerService.save(customer);
        return "redirect:/admin/customers";
    }
    
    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "admin/customer-form";
    }
    
    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable Integer id, 
                                @ModelAttribute Customer customer,
                                Model model) {
        Customer existingCustomer = customerService.findById(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setUsername(customer.getUsername());
        existingCustomer.setRole(customer.getRole());
        
        // Chỉ update password nếu có nhập mới
        if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        
        customerService.save(existingCustomer);
        return "redirect:/admin/customers";
    }
    
    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
        return "redirect:/admin/customers";
    }
    
    // ========== QUẢN LÝ DANH MỤC ==========
    @GetMapping("/categories")
    public String adminCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }
    
    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new com.example.model.Category());
        return "admin/category-form";
    }
    
    @PostMapping("/categories/add")
    public String addCategory(@Valid @ModelAttribute com.example.model.Category category, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        categoryService.save(category);
        return "redirect:/admin/categories";
    }
    
    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category-form";
    }
    
    @PostMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable Integer id, 
                                @Valid @ModelAttribute com.example.model.Category category,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        category.setId(id);
        categoryService.save(category);
        return "redirect:/admin/categories";
    }
    
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }
}
