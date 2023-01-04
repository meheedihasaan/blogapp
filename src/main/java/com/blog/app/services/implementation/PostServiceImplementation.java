package com.blog.app.services.implementation;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.PostDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    //To create a post
    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        //To fetch Creator and Category of the post
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = this.dtoToPost(postDto);
        post.setImageUrl("postImage.jpg");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = this.postRepository.save(post);
        return this.postToDto(savedPost);
    }

    //To get all the posts
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepository.findAll();
        List<PostDto> postsDto = posts
                                    .stream()
                                    .map(post-> this.postToDto(post))
                                    .collect(Collectors.toList());
        return postsDto;
    }

    //To get a post by its id
    @Override
    public PostDto getPostById(int id) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return this.postToDto(post);
    }

    //To update a post
    @Override
    public PostDto updatePost(PostDto postDto, int id) {
        Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        post.setCategory(this.modelMapper.map(postDto.getCategory(), Category.class)); //Convert CategoryDto to Category

        Post updatedPost = this.postRepository.save(post);
        return this.postToDto(updatedPost);
    }

    //To delete a post by its id
    @Override
    public void deletePost(int id) {
        Post post = this.postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        this.postRepository.delete(post);
    }

    //To get posts by their creator
    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDto> postsDto = posts
                                    .stream()
                                    .map(post-> this.postToDto(post))
                                    .collect(Collectors.toList());
        return postsDto;
    }

    //To get posts by their category
    @Override
    public List<PostDto> getPostByCategory(int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        List<PostDto> postsDto = posts
                                    .stream()
                                    .map(post-> this.postToDto(post))
                                    .collect(Collectors.toList());
        return postsDto;
    }

    //To search a post by keyword
    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }

    //To convert PostDto to Post
    public Post dtoToPost(PostDto postDto){
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }

    //To convert Post to PostDto
    public PostDto postToDto(Post post){
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

}
