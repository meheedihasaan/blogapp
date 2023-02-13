package com.blog.app.controllers;

import com.blog.app.helper.Message;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.services.CategoryService;
import com.blog.app.services.FileService;
import com.blog.app.services.PostService;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin-panel/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;
    @Autowired
    private CategoryRepository categoryRepository;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username);  //Email is used as username
        model.addAttribute("user", user);
    }

    @GetMapping("/create-post")
    public String viewCreatePostPage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Create Post");
        loadCommonData(model, principal);

        List<CategoryDto> categories = this.categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("post", new PostDto());

        return "admin-template/create-post";
    }

    @PostMapping("/create-post/process")
    public String createPost(
            @Valid @ModelAttribute("post") PostDto post, BindingResult bindingResult,
            @RequestParam(value = "categoryId", defaultValue = "0", required = true) int categoryId,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Create Post");
        loadCommonData(model, principal);
        try {
            if (bindingResult.hasErrors()) {
                List<CategoryDto> categories = this.categoryService.getAllCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("post", post);
                return "admin-template/create-post";
            }

            //I have commented out this code for production only
//            if(imageFile.isEmpty()) {
//                redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Please upload a feature image with your post."));
//                return "redirect:/admin-panel/posts/create-post";
//            }

            //String imageUrl = this.fileService.uploadImage(imageFile);
            String imageUrl = "default.jpg";
            this.postService.createPost(post, principal.getName(), categoryId, imageUrl);
            redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Post is created successfully."));
            return "redirect:/admin-panel/posts/create-post";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/posts/create-post";
        }
    }

    @GetMapping("/my-posts/all/{page}")
    public String getAllPosts(@PathVariable int page, Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - My Posts");
        loadCommonData(model, principal);

        UserDto user = this.userService.getUserByEmail(principal.getName());
        Page<PostDto> postDtoPage = this.postService.getPostsByUser(user.getId(), page, 5, "date", "desc");
        model.addAttribute("postDtoPage", postDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDtoPage.getTotalPages());
        return "admin-template/my-posts";
    }

    @GetMapping("/my-posts/{id}/{title}")
    public String viewPost(@PathVariable int id, Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - View Post");
        loadCommonData(model , principal);

        PostDto post = this.postService.getPostById(id);
        model.addAttribute("post", post);
        return "admin-template/view-post";
    }

    @GetMapping("/my-posts/{id}/{title}/edit")
    public String viewEditPostPage(@PathVariable int id, Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Edit Post");
        loadCommonData(model, principal);

        PostDto post = this.postService.getPostById(id);
        List<CategoryDto> categories = this.categoryService.getAllCategories();
        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        return "admin-template/edit-post";
    }

    @PostMapping("/my-posts/{id}/edit-process")
    public String viewEditPostPage(
            @Valid @ModelAttribute("post") PostDto post, BindingResult bindingResult,
            @RequestParam(value = "categoryId", defaultValue = "0", required = true) int categoryId,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Edit Post");
        loadCommonData(model, principal);
        try {
            if (bindingResult.hasErrors()) {
                List<CategoryDto> categories = this.categoryService.getAllCategories();
                CategoryDto category = this.categoryService.getCategoryByID(categoryId);
                post.setCategory(category);                     //To view category even if there is binding error
                //PostDto existingPost = this.postService.getPostById(post.getId());
                //post.setImageUrl(existingPost.getImageUrl());   //To view image even if there is binding error
                model.addAttribute("post", post);
                model.addAttribute("categories", categories);
                return "admin-template/edit-post";
            }

            //I have commented out this code for production only
//            if(!imageFile.isEmpty()) {
//                String imageUrl = this.fileService.uploadImage(imageFile);
//                post.setImageUrl(imageUrl);
//            }

            CategoryDto category = this.categoryService.getCategoryByID(categoryId);
            post.setCategory(category);
            PostDto updatedPost = this.postService.updatePost(post, post.getId());
            redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Post is updated successfully."));
            return "redirect:/admin-panel/posts/my-posts/all/0";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/posts/my-posts/all/0";
        }

    }

    @GetMapping("/my-posts/{id}/{title}/delete")
    public String deleteMyPost(@PathVariable int id, RedirectAttributes redirectAttributes, Principal principal){
        try {
            UserDto user = this.userService.getUserByEmail(principal.getName());
            this.postService.deletePost(id, user.getId());
            redirectAttributes.addFlashAttribute("message", new Message("alert-success", "Post is deleted successfully."));
            return "redirect:/admin-panel/posts/my-posts/all/0";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/posts/my-posts/all/0";
        }
    }


}
