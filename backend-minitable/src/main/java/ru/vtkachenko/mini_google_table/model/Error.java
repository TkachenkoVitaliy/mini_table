package ru.vtkachenko.mini_google_table.model;

public class Error {

    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error() {
    }

    public Error(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
