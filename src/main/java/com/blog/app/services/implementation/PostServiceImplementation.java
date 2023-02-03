package com.blog.app.services.implementation;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.helper.PostResponse;
import com.blog.app.payloads.PostDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public Page getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        //To sort
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if(sortDirection.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }

        //For pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = this.postRepository.findAll(pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts
                                .stream()
                                .map((post)-> this.postToDto(post))
                                .collect(Collectors.toList());

        return new PageImpl(postsDto, pageable, page.getTotalElements());
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
    public Page<PostDto> getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        //To sort
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if(sortDirection.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }

        //For Pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page page = this.postRepository.findByUser(user, pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts
                                .stream()
                                .map(post-> this.postToDto(post))
                                .collect(Collectors.toList());

        return new PageImpl<PostDto>(postsDto, pageable, page.getTotalElements());
    }

    //To get posts by their category
    @Override
    public Page<PostDto> getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        //To sort
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if(sortDirection.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }

        //For pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page page = this.postRepository.findByCategory(category, pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts
                                .stream()
                                .map(post-> this.postToDto(post))
                                .collect(Collectors.toList());

        return new PageImpl<PostDto>(postsDto, pageable, page.getTotalElements());
    }

    //To search a post by keyword
    @Override
    public PostResponse searchPosts(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page page = this.postRepository.searchByTitleContaining(keyword, pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postsDto = posts
                                    .stream()
                                    .map((post)-> this.postToDto(post))
                                    .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostsDto(postsDto);
        postResponse.setPageNumber(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalPost((int)page.getTotalElements());
        postResponse.setTotalPage(page.getTotalPages());
        postResponse.setLastPage(page.isLast());
        return postResponse;
    }

    //To get banner posts for home page
    @Override
    public List<PostDto> getBannerPosts() {
        List<Post> posts = this.postRepository.findAll();
        List<PostDto> postsDto = posts
                .stream()
                .map((post)-> this.postToDto(post))
                .collect(Collectors.toList());

        List<PostDto> bannerPosts = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < 5) {
            set.add(random.nextInt(postsDto.size()));
        }

        for (int i : set) {
            bannerPosts.add(postsDto.get(i));
        }
        return bannerPosts;
    }

    //To get featured posts
    @Override
    public List<PostDto> getFeaturedPost() {
        List<Post> posts = this.postRepository.findAll();
        List<PostDto> postsDto = posts
                                .stream()
                                .map((post)-> this.postToDto(post))
                                .collect(Collectors.toList());

        List<PostDto> featuredPosts = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < 4) {
            set.add(random.nextInt(postsDto.size()));
        }

        for (int i : set) {
            featuredPosts.add(postsDto.get(i));
        }
        return featuredPosts;
    }

    //To get related posts
    @Override
    public List<PostDto> getRelatedPosts(int postId, int categoryId) {
        Post currentPost = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        posts.remove(currentPost);
        List<PostDto> postsDto = posts
                .stream()
                .map((post)-> this.postToDto(post))
                .collect(Collectors.toList());

        if(postsDto.size() <= 4) {
            return postsDto;
        }

        List<PostDto> relatedPosts = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < 4) {
            set.add(random.nextInt(postsDto.size()));
        }

        for (int i : set) {
            relatedPosts.add(postsDto.get(i));
        }
        return relatedPosts;
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
