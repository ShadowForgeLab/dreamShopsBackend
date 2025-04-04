package com.project.dreamshops.response;

import com.project.dreamshops.model.Product;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String categoryName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.inventory = product.getInventory();
        this.description = product.getDescription();
        this.categoryName = product.getCategory().getName();
    }

    // Getters & Setters
}
