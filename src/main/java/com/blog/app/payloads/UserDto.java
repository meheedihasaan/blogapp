package com.blog.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    @NotEmpty
    @Size(min = 4, max = 32, message = "Password must be between 4 to 32 characters.")
    private String password;

    @Size(min = 0, max = 10000, message = "Description must contain less than or equal 10000 characters.")
    private String description;

}
