package com.example.LibraryManagement.error;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("Book id not found : " + id);
    }

}
