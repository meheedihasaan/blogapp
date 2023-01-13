package com.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int id;

    @NotEmpty(message = "Post title is required.")
    @Size(min = 10, max = 255, message = "Post title length must be between 10 to 255 characters.")
    private String title;

    @NotEmpty(message = "Post content is required.")
    @Size(min = 200, max = 10000, message = "Post content length must be between 200 to 10000 characters.")
    private String content;

    private String imageUrl;

    private Date date;

    private UserDto user;

    private CategoryDto category;

    private List<CommentDto> comments = new ArrayList<>();

}
