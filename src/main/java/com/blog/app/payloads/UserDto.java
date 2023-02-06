package com.blog.app.payloads;

import com.blog.app.entities.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty(message = "Name must be required.")
    @Size(min = 4, max = 50, message = "Name must be between 4 to 50 characters.")
    private String name;

    @Email(message = "Email is not valid! Enter a valid email.")
    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Password is required.")
    @Size(min = 4, max = 32, message = "Password must be between 4 to 32 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "Description is required.")
    @Size(min = 10, max = 10000, message = "Description must be between 10 to 1000 characters.")
    private String description;

    List<RoleDto> roles = new ArrayList<>();

}
