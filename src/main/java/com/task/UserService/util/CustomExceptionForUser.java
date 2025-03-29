package com.task.UserService.util;

public class CustomExceptionForUser extends RuntimeException {
    public CustomExceptionForUser(String message) {
        super(message);
    }
}
