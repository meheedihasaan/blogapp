package com.blog.app.controllers;

import com.blog.app.entities.Post;
import com.blog.app.payloads.PostDto;
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

    @GetMapping("/posts/{page}")
    public String getAllPosts(@PathVariable int page, Model model) {
        model.addAttribute("title", "Posts");

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


}
