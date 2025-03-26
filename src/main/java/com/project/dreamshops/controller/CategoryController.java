package com.project.dreamshops.controller;

import com.project.dreamshops.exception.AlreadyExistsException;
import com.project.dreamshops.model.Category;
import com.project.dreamshops.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> addCategory(@RequestBody Category category){
        try {
            Category category1 = categoryService.addCategory(category);
            return new ResponseEntity<>(category1,HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>("Failed to add Category it already exists",HttpStatus.CONFLICT);
        }
    }
}
