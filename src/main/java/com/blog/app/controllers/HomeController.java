package com.blog.app.controllers;

import com.blog.app.entities.Post;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.payloads.PostDto;
import com.blog.app.services.CategoryService;
import com.blog.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("title", "Mini Blog");

        //Banner Posts
        List<PostDto> bannerPosts = this.postService.getBannerPosts();
        model.addAttribute("bannerPosts", bannerPosts);

        return "blog-template/index";
    }

    @GetMapping("/posts/{page}")
    public String getAllPosts(@PathVariable int page, Model model) {
        model.addAttribute("title", "Mini Blog - Posts");

        //All Posts
        Page<Post> posts = this.postService.getAllPosts(page, 3, "date", "desc");
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());

        //Featured Posts
        List<PostDto> featuredPosts = this.postService.getFeaturedPost();
        model.addAttribute("featuredPosts", featuredPosts);

        return "blog-template/posts";
    }

    @GetMapping("/categories")
    public String viewAllCategories(Model model) {
        model.addAttribute("title", "Mini Blog - Categories");

        List<CategoryDto> categories = this.categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "blog-template/categories";
    }

    @GetMapping("/categories/{id}/{title}/{page}")
    public String viewCategory(@PathVariable int id, @PathVariable int page, Model model) {
        CategoryDto category = this.categoryService.getCategoryByID(id);
        model.addAttribute("title", "Mini Blog - "+category.getTitle());
        model.addAttribute("category", category);

        Page<PostDto> postDtoPage = this.postService.getPostByCategory(id, page, 3, "date", "desc");
        model.addAttribute("postDtoPage", postDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDtoPage.getTotalPages());

        return "blog-template/single-category";
    }

    @GetMapping("/posts/{id}/{title}")
    public String viewSiglePost(@PathVariable int id, Model model) {
        model.addAttribute("title", "Min Blog - Read Post");

        PostDto post = this.postService.getPostById(id);
        model.addAttribute("post", post);

        return "blog-template/single-post";
    }


}
