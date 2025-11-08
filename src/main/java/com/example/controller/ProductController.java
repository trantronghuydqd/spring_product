package com.example.controller;

import com.example.model.Product;
import com.example.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping
    public String listProducts(@RequestParam(required = false) String search, Model model) {
        List<Product> products = search != null ? 
            productService.search(search) : productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        return "products/list";
    }
    
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("comments", commentService.findByProductId(id));
        return "products/detail";
    }
    
    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Integer id, 
                            @RequestParam String text) {
        commentService.addComment(id, text);
        return "redirect:/products/" + id;
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        return "redirect:/products?search=" + keyword;
    }
    
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Integer id, 
                           @RequestParam(defaultValue = "1") Integer quantity,
                           HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart.put(id, cart.getOrDefault(id, 0) + quantity);
        session.setAttribute("cart", cart);
        return "redirect:/products";
    }
    
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            List<Product> cartProducts = new ArrayList<>();
            for (Integer productId : cart.keySet()) {
                cartProducts.add(productService.findById(productId));
            }
            model.addAttribute("cartProducts", cartProducts);
            model.addAttribute("cart", cart);
        }
        return "products/cart";
    }
    
    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Integer id, HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/products/cart";
    }
}
