package com.blog.app.services.implementation;

import com.blog.app.entities.Comment;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CommentDto;
import com.blog.app.repositories.CommentRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //To create a comment
    @Override
    public CommentDto createComment(CommentDto commentDto, int userId, int postId) {
        //To fetch post and creator of the comment
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = this.dtoToComment(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepository.save(comment);
        return this.commentToDto(savedComment);
    }

    //To delete a comment
    @Override
    public void deleteComment(int id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", id));
        this.commentRepository.delete(comment);
    }

    //To convert CommentDto to Comment
    public Comment dtoToComment(CommentDto commentDto){
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    //To convert Comment to CommentDto
    public CommentDto commentToDto(Comment comment){
        CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

}
