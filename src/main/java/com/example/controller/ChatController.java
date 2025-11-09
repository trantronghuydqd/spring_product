package com.example.controller;

import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/chat")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @GetMapping
    public String chatPage(HttpSession session, Model model) {
        List<Map<String, String>> messages = (List<Map<String, String>>) session.getAttribute("chatMessages");
        if (messages == null) {
            messages = new ArrayList<>();
            // Thêm tin nhắn chào mừng
            Map<String, String> welcomeMsg = new HashMap<>();
            welcomeMsg.put("type", "ai");
            welcomeMsg.put("text", "👋 Xin chào! Tôi là trợ lý AI của cửa hàng. Tôi có thể giúp bạn tìm kiếm và tư vấn sản phẩm điện tử. Bạn cần tư vấn gì?");
            messages.add(welcomeMsg);
            session.setAttribute("chatMessages", messages);
        }
        model.addAttribute("messages", messages);
        return "chat";
    }
    
    @PostMapping("/message")
    public String sendMessage(@RequestParam String message, HttpSession session) {
        List<Map<String, String>> messages = (List<Map<String, String>>) session.getAttribute("chatMessages");
        if (messages == null) {
            messages = new ArrayList<>();
        }
        
        // Thêm tin nhắn của user
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("type", "user");
        userMsg.put("text", message);
        messages.add(userMsg);
        
        // Gọi AI và thêm response
        String aiResponse = chatService.chat(message);
        Map<String, String> aiMsg = new HashMap<>();
        aiMsg.put("type", "ai");
        aiMsg.put("text", aiResponse);
        messages.add(aiMsg);
        
        session.setAttribute("chatMessages", messages);
        
        return "redirect:/chat";
    }
    
    @GetMapping("/clear")
    public String clearChat(HttpSession session) {
        session.removeAttribute("chatMessages");
        return "redirect:/chat";
    }
}
