package com.project.dreamshops.request;

import com.project.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;
    private Category category;
}
