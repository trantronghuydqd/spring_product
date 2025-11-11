package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String text;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
