package com.blog.app.controllers;

import com.blog.app.helper.Message;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
                return "redirect:/admin-panel/categories/create-category";
            }

            this.categoryService.createCategory(category);
            redirectAttributes.addFlashAttribute("message", new Message("alert-primary","Category is created successfully."));
            return "redirect:/admin-panel/categories/create-category";
        }
        catch (Exception e) {
            model.addAttribute("category", category);
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/categories/create-category";
        }
    }

    @GetMapping("/all/{page}")
    public String getAllCategories(@PathVariable int page, Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - View Categories");
        loadCommonData(model, principal);

        Page<CategoryDto> categoryDtoPage = this.categoryService.getAllCategories(page, 5, "title", "asc");
        model.addAttribute("categoryDtoPage", categoryDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryDtoPage.getTotalPages());

        return "admin-template/categories";
    }

    @GetMapping("/{id}/{title}/edit")
    public String viewEditCategoryPage(@PathVariable int id, Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Edit Category");
        loadCommonData(model, principal);

        CategoryDto category = this.categoryService.getCategoryByID(id);
        model.addAttribute("category", category);

        return "admin-template/edit-category";
    }

    @PostMapping("/{id}/edit-process")
    public String editCategory(
            @Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Edit Category");
        loadCommonData(model, principal);
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("category", category);
                return "admin-template/edit-category";
            }

            this.categoryService.updateCategory(category, category.getId());
            redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Category is updated successfully."));

            return "redirect:/admin-panel/categories/all/0";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger" ,"Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/categories/all/0";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/{title}/delete")
    public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", new Message("alert-success", "Category is deleted successfully."));
            return "redirect:/admin-panel/categories/all/0";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/categories/all/0";
        }
    }

}
