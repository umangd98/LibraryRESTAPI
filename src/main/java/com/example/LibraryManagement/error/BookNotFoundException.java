package com.example.LibraryManagement.error;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int id) {
        super("Book id not found : " + id);
    }

}