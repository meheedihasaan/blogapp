package com.blog.app.controllers;

import com.blog.app.payloads.PostDto;
import com.blog.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    //To create a post
    @PostMapping("/user/{userId}/category/{categoryId}/createPost")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
        PostDto savedPostDto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(savedPostDto, HttpStatus.CREATED);
    }

    //To get all the posts
    @GetMapping("/posts/all")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtos = this.postService.getAllPosts();
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    //To get a post by its id
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id){
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    //To update a post

    //To delete a post

    //To get posts by their creator
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
        List<PostDto> postDtos = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
    }

    //To get posts their category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int categoryId){
        List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

}
