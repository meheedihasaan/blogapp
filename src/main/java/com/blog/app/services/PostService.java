package com.blog.app.services;

import com.blog.app.payloads.PostDto;

import java.util.List;

public interface PostService {

    //To create a post
    public PostDto createPost(PostDto postDto, int userId, int categoryId);

    //To get all the posts
    public List<PostDto> getAllPosts();

    //To get a post by its id
    public PostDto getPostById(int id);

    //To update a post
    public PostDto updatePost(PostDto postDto, int id);

    //To delete a post by its id
    public void deletePost(int id);

    //To get posts by their creator
    public List<PostDto> getPostsByUser(int userId);

    //To get posts by their category
    public List<PostDto> getPostByCategory(int categoryId);

    //To search a post by keyword
    public List<PostDto> searchPosts(String keyword);


}
