package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin-panel/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/create-category")
    public String viewCreateCategoryPage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Create Category");

        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username); //Email is used as username
        model.addAttribute("user", user);

        return "admin-template/create-category";
    }

    //To create a category
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    //To get all the categories
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoriesDto = this.categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    //To get a category by its id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id){
        CategoryDto categoryDto = this.categoryService.getCategoryByID(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    //To update a category
    @PutMapping("/{id}/update")
    public  ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int id){
        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    //To delete a category by its id
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully.", true), HttpStatus.OK);
    }

}
