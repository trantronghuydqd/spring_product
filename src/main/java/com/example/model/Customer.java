package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Tên không được để trống")
    private String name;
    
    @NotBlank(message = "Username không được để trống")
    @Column(unique = true)
    private String username;
    
    @NotBlank(message = "Password không được để trống")
    private String password;
    
    @Temporal(TemporalType.DATE)
    private Date customerSince;
    
    private String role; // ADMIN, CUSTOMER
    
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
