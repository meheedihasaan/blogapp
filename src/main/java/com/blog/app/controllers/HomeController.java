package com.blog.app.controllers;

import com.blog.app.entities.Post;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.PostService;
import com.blog.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Mini Blog");

        //Banner Posts
        List<PostDto> bannerPosts = this.postService.getBannerPosts();
        model.addAttribute("bannerPosts", bannerPosts);

        return "blog-template/index";
    }

    @GetMapping("/posts/all/{page}")
    public String getAllPosts(@PathVariable int page, Model model) {
        model.addAttribute("title", "Mini Blog - Posts");

        //All Posts
        Page<PostDto> postDtoPage = this.postService.getAllPosts(page, 3, "date", "desc");
        model.addAttribute("postDtoPage", postDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDtoPage.getTotalPages());

        //Featured Posts
        List<PostDto> featuredPosts = this.postService.getFeaturedPost();
        model.addAttribute("featuredPosts", featuredPosts);

        return "blog-template/posts";
    }

    @GetMapping("/categories")
    public String viewAllCategories(Model model) {
        model.addAttribute("title", "Mini Blog - Categories");

        //All Categories
        List<CategoryDto> categories = this.categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "blog-template/categories";
    }

    @GetMapping("/categories/{id}/{title}/{page}")
    public String viewCategory(@PathVariable int id, @PathVariable int page, Model model) {
        //Single Category
        CategoryDto category = this.categoryService.getCategoryByID(id);
        model.addAttribute("title", "Mini Blog - "+category.getTitle());
        model.addAttribute("category", category);

        //Category Posts
        Page<PostDto> postDtoPage = this.postService.getPostByCategory(id, page, 3, "date", "desc");
        model.addAttribute("postDtoPage", postDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDtoPage.getTotalPages());

        return "blog-template/single-category";
    }

    @GetMapping("/posts/{id}/{title}")
    public String viewSinglePost(@PathVariable int id, Model model) {
        model.addAttribute("title", "Min Blog - Read Post");

        //Single Post
        PostDto post = this.postService.getPostById(id);
        model.addAttribute("post", post);

        //Related Posts
        int postId = post.getId();
        int categoryId = post.getCategory().getId();
        List<PostDto> relatedPosts = this.postService.getRelatedPosts(postId, categoryId);
        model.addAttribute("relatedPosts", relatedPosts);

        return "blog-template/single-post";
    }

    @GetMapping("/authors/{id}/{name}/{page}")
    public String viewAuthor(@PathVariable int id, @PathVariable int page, Model model) {
        UserDto user = this.userService.getUserById(id);
        model.addAttribute("title", "Mini Blog - "+user.getName());
        model.addAttribute("user", user);

        Page<PostDto> postDtoPage = this.postService.getPostsByUser(id, page, 3, "date", "desc");
        model.addAttribute("postDtoPage", postDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDtoPage.getTotalPages());

        return "blog-template/author";
    }


}
