package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.helper.PostResponse;
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
    @PostMapping("/user/{userId}/category/{categoryId}/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
        PostDto savedPostDto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    //To get all the posts
    @GetMapping("/posts/all")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ){
        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //To get a post by its id
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id){
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //To update a post
    @PutMapping("/posts/{id}/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int id){
        PostDto updatedPostDto = this.postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    //To delete a post
    @DeleteMapping("/posts/{id}/delete")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int id){
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully.", true), HttpStatus.OK);
    }

    //To get posts by their creator
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(
            @PathVariable int userId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize
    ){
        PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //To get posts their category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable int categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize
    ){
        PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

}
