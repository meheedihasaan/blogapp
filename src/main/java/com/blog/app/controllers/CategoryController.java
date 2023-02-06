package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.helper.Message;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin-panel/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username); //Email is used as username
        model.addAttribute("user", user);
    }

    @GetMapping("/create-category")
    public String viewCreateCategoryPage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Create Category");
        loadCommonData(model, principal);
        model.addAttribute("category", new CategoryDto());

        return "admin-template/create-category";
    }

    @PostMapping("/create-category/process")
    public String createCategory(
            @Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Create Category");
        loadCommonData(model, principal);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("category", category);
                return "admin-template/create-category";
            }

            CategoryDto existing = this.categoryService.getCategoryByTitleIgnoreCase(category.getTitle());
            if (existing != null) {
                redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "The category name you entered is already stored. Please create a new category."));
            }
            else {
                this.categoryService.createCategory(category);
                redirectAttributes.addFlashAttribute("message", new Message("alert-primary","Category is created successfully."));
            }

            return "redirect:/admin-panel/categories/create-category";

        }
        catch (Exception e) {
            model.addAttribute("category", category);
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/categories/create-category";
        }
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
