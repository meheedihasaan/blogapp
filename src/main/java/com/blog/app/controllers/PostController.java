package com.blog.app.controllers;

import com.blog.app.configs.AppConstants;
import com.blog.app.helper.ApiResponse;
import com.blog.app.helper.Message;
import com.blog.app.helper.PostResponse;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.PostService;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/admin-panel/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

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
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Create Post");
        loadCommonData(model, principal);
        try {
            if (bindingResult.hasErrors()) {
                System.out.println(bindingResult.toString());
                List<CategoryDto> categories = this.categoryService.getAllCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("post", post);
                return "admin-template/create-post";
            }

            CategoryDto category = this.categoryService.getCategoryByID(categoryId);
            post.setCategory(category);
            System.out.println(post.getTitle()+" "+post.getCategory().getTitle()+" "+post.getImageUrl());
            redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Post is created successfully."));
            return "redirect:/admin-panel/posts/create-post";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-daanger", "Something went wrong. "+e.getMessage()));
            return "redirect:/admin-panel/posts/create-post";
        }
    }

    //To create a post
    @PostMapping("/user/{userId}/category/{categoryId}/create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
        PostDto savedPostDto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    //To get all the posts
    @GetMapping("/posts/all")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ){
        //PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //To get a post by its id
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id){
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //To update a post
    @PutMapping("/posts/{id}/update")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable int id){
        PostDto updatedPostDto = this.postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    //To delete a post
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}/delete")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int id){
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully.", true), HttpStatus.OK);
    }

    //To get posts by their creator
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<Page<PostDto>> getPostsByUser(
            @PathVariable int userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ){
        Page<PostDto> postDtoPage = this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postDtoPage, HttpStatus.OK);
    }

    //To get posts their category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<Page<PostDto>> getPostByCategory(
            @PathVariable int categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ){
        Page<PostDto> postDtoPage = this.postService.getPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postDtoPage, HttpStatus.OK);
    }

    //To search posts by title
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<PostResponse> searchPosts(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize
    ){
        PostResponse postResponse = this.postService.searchPosts(keyword, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

}
