package com.example.service;

import com.example.model.Comment;
import com.example.model.Product;
import com.example.repository.CommentRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Comment> findByProductId(Integer productId) {
        return commentRepository.findByProductIdAndIsActiveTrueOrderByIdDesc(productId);
    }
    
    public List<Comment> findByProductIdIncludingInactive(Integer productId) {
        return commentRepository.findByProductIdOrderByIdDesc(productId);
    }
    
    public Comment addComment(Integer productId, String text) {
        Product product = productRepository.findById(productId).orElse(null);
        
        if (product == null) {
            return null;
        }
        
        Comment comment = new Comment();
        comment.setProduct(product);
        comment.setText(text);
        comment.setIsActive(true);
        
        return commentRepository.save(comment);
    }
    
    public void deleteById(Integer id) {
        // Soft delete: set isActive = false
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setIsActive(false);
            commentRepository.save(comment);
        }
    }
    
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }
}
