package com.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int id;

    @NotEmpty(message = "Empty comment not allowed.")
    @Size(min = 1, max = 1000, message = "Comment must be between 1 to 1000 characters.")
    private String content;

    private UserDto user;

}
