package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.payloads.CommentDto;
import com.blog.app.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //To create a comment
    @PostMapping("/users/{userId}/posts/{postId}/createComment")
    public ResponseEntity<CommentDto> createComment(
            @Valid
            @RequestBody CommentDto commentDto,
            @PathVariable int userId,
            @PathVariable int postId
    ){
        CommentDto savedCommentDto = this.commentService.createComment(commentDto, userId, postId);
        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }

    //To delete a comment
    @DeleteMapping("/comments/{id}/deleteComment")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int id){
        this.commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully.", true), HttpStatus.OK);
    }

}
