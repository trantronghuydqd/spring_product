package com.example.service;

import com.example.model.Product;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {
    
    @Autowired
    private ChatModel chatModel;
    
    @Autowired
    private ProductService productService;
    
    public String chat(String userMessage) {
        // Get products for context
        List<Product> products = productService.findAll();
        
        // Build context about products
        StringBuilder context = new StringBuilder();
        context.append("Bạn là trợ lý AI tư vấn sản phẩm của cửa hàng điện tử.\n");
        context.append("Danh sách sản phẩm hiện có:\n\n");
        
        for (Product p : products) {
            context.append(String.format("- %s (%s): %,d VNĐ - %s\n", 
                p.getName(), 
                p.getCategory().getName(),
                p.getPrice().longValue(),
                p.getInStock() ? "Còn hàng" : "Hết hàng"
            ));
        }
        
        context.append("\nHãy tư vấn sản phẩm phù hợp dựa trên câu hỏi của khách hàng. ");
        context.append("Trả lời bằng tiếng Việt, ngắn gọn, thân thiện và chuyên nghiệp.\n\n");
        context.append("Khách hàng hỏi: ").append(userMessage);
        
        // Call Ollama AI
        try {
            String response = chatModel.call(new Prompt(context.toString()))
                .getResult()
                .getOutput()
                .getContent();
            return response;
        } catch (Exception e) {
            return "Xin lỗi, tôi đang gặp sự cố. Vui lòng thử lại sau. (Lỗi: " + e.getMessage() + ")";
        }
    }
}
