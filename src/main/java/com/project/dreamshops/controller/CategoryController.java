package com.project.dreamshops.controller;

import com.project.dreamshops.exception.AlreadyExistsException;
import com.project.dreamshops.model.Category;
import com.project.dreamshops.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        try {
            Category category1 = categoryService.addCategory(category);
            return new ResponseEntity<>(category1,HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>("Failed to add Category it already exists",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){

        try {
            Category category = categoryService.getCategoryById(id);
            return new  ResponseEntity<>(category,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategory/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        try{
            Category category = categoryService.getCategoryByName(name);
            return new ResponseEntity<>(category,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Some error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category,@PathVariable Long id){
        try{
            Category category1 = categoryService.updateCategory(category,id);
            return new ResponseEntity<>(category1,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Some error occcurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            Category category =categoryService.getCategoryById(id);
            if(category!=null){
                categoryService.deleteCategoryById(id);
                return new ResponseEntity<>("Category with id ${id} deleted",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No such category present",HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Some error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
