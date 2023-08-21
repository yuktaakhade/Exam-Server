package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //add Category
    @PostMapping("/")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        Category category1 = categoryService.addCategory(category);
        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Long categoryId){
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    //get all categories
    @GetMapping("/")
    public ResponseEntity<?> getCategories(){
        return  ResponseEntity.ok(categoryService.getCategories());
    }

    //update
    @PutMapping("/")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category id: "+categoryId+" deleted",HttpStatus.OK);
    }

}
