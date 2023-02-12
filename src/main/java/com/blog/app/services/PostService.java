package com.blog.app.services;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.helper.PostResponse;
import com.blog.app.payloads.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    //To create a post
    public PostDto createPost(PostDto postDto, String username, int categoryId, String imageUrl);

    //To get all the posts
    public Page<PostDto> getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To get a post by its id
    public PostDto getPostById(int id);

    //To update a post
    public PostDto updatePost(PostDto postDto, int id);

    //To delete a post by its id
    public void deletePost(int postId, int userId) throws Exception;

    //To get posts by their creator
    public Page<PostDto> getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To get posts by their creator in dashboard
    public List<PostDto> getPostsByUser(int userId, String sortBy, String sortDirection);

    //To get posts by their category
    public Page<PostDto> getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    //To search a post by keyword
    public PostResponse searchPosts(String keyword, int pageNumber, int pageSize);

    //To get banner posts for home page
    List<PostDto> getBannerPosts();

    //To get featured posts
    List<PostDto> getFeaturedPost();

    List<PostDto> getRelatedPosts(int postId, int categoryId);

}
