package com.blog.app.helper;

import com.blog.app.payloads.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> postsDto;

    private int pageNumber;

    private int pageSize;

    private int totalPost;

    private int totalPage;

    private boolean isLastPage;

}
