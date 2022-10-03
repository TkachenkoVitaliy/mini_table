package ru.vtkachenko.mini_google_table.exception;

public class ExpressionException extends RuntimeException{
    public String cellAddress = null;

    public ExpressionException(String message) {
        super(message);
    }
}
