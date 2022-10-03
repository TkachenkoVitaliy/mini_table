package ru.vtkachenko.mini_google_table.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Error {

    private int statusCode;
    private String message;
    private String cellAddress;

    @JsonGetter("statusCode")
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonGetter("cellAddress")
    public String getCellAddress() {
        return cellAddress;
    }

    public void setCellAddress(String cellAddress) {
        this.cellAddress = cellAddress;
    }

    public Error() {
    }

    public Error(int statusCode, String message, String cellAddress) {
        this.statusCode = statusCode;
        this.message = message;
        this.cellAddress = cellAddress;
    }
}
