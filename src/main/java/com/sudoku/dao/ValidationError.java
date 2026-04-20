package com.sudoku.dao;

//This class is a simple error structure that is returned in case of failed validations. 

public class ValidationError {
    private final String message;

    public ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
