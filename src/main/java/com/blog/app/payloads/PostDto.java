package com.blog.app.payloads;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int id;

    private String title;

    private String content;

    private String imageUrl;

    private Date date;

    private UserDto user;

    private CategoryDto category;

    private List<CommentDto> comments = new ArrayList<>();

}
