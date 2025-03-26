package com.project.dreamshops.service;

import com.project.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String Name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategoryById(Long id);

}
