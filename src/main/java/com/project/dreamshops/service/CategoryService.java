package com.project.dreamshops.service;

import com.project.dreamshops.exception.AlreadyExistsException;
import com.project.dreamshops.exception.ResourceNotFoundException;
import com.project.dreamshops.model.Category;
import com.project.dreamshops.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String Name) {
        return categoryRepo.findByName(Name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepo.existsByName(category.getName()))
                .map(categoryRepo::save)
                .orElseThrow(()->new AlreadyExistsException(category.getName()+" already exists"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory-> {
                    oldCategory.setName(category.getName());
                    return categoryRepo.save(oldCategory);
                }).
                orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{throw new ResourceNotFoundException("Category Not Found");});
    }
}
