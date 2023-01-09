package com.blog.app.services;

import com.blog.app.payloads.CommentDto;

public interface CommentService {

    //To create a comment
    public CommentDto createComment(CommentDto commentDto, int userId, int postId);

    //To delete a comment
    public void deleteComment(int id);

    //No need to fetch comment separately. Because all the comments are fetched with their corresponding post.

}
