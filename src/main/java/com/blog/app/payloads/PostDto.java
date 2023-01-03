package com.blog.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

}
