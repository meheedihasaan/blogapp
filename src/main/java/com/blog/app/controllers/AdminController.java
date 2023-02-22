package com.blog.app.controllers;

import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.PostService;
import com.blog.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin-panel")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username);  //Email is used as username
        model.addAttribute("user", user);
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Dashboard");
        loadCommonData(model, principal);

        UserDto user = this.userService.getUserByEmail(principal.getName());
        List<CategoryDto> categories = this.categoryService.getAllCategories();
        List<PostDto> posts = this.postService.getPostsByUser(user.getId(), "date", "desc");
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);

        return "admin-template/index";
    }

    @GetMapping("/profile/{username}")
    public String viewProfilePage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Profile");
        loadCommonData(model, principal);

        UserDto user = this.userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "admin-template/profile";
    }

}
