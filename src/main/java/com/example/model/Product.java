package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, message = "Tên phải có ít nhất 2 ký tự")
    @Pattern(regexp = "^[a-zA-Z].*", message = "Tên phải bắt đầu bằng chữ")
    private String name;
    
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;
    
    private Boolean inStock;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category không được để trống")
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}
