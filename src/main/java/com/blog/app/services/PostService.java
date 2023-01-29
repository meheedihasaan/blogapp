package com.blog.app.services;

import com.blog.app.helper.PostResponse;
import com.blog.app.payloads.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    //To create a post
    public PostDto createPost(PostDto postDto, int userId, int categoryId);

    //To get all the posts
    public Page getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To get a post by its id
    public PostDto getPostById(int id);

    //To update a post
    public PostDto updatePost(PostDto postDto, int id);

    //To delete a post by its id
    public void deletePost(int id);

    //To get posts by their creator
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To get posts by their category
    public PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To search a post by keyword
    public PostResponse searchPosts(String keyword, int pageNumber, int pageSize);


}
