package com.project.dreamshops.service;

import com.project.dreamshops.model.Product;
import com.project.dreamshops.request.AddProductRequest;
import com.project.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long id);
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String name,String brand);
    Long countProductByBrandAndName(String brand,String name);
}
