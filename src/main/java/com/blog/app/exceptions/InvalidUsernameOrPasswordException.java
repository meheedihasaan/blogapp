package com.blog.app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidUsernameOrPasswordException extends RuntimeException {

    private String message;
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
        this.message = message;
    }

}
