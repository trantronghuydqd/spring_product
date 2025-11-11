package com.example.service;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> findAll() {
        return categoryRepository.findByIsActiveTrue();
    }
    
    public List<Category> findAllIncludingInactive() {
        return categoryRepository.findAll();
    }
    
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
    
    public Category save(Category category) {
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }
        return categoryRepository.save(category);
    }
    
    public void deleteById(Integer id) {
        // Soft delete: set isActive = false
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setIsActive(false);
            categoryRepository.save(category);
        }
    }
    
    public long count() {
        return categoryRepository.count();
    }
}
