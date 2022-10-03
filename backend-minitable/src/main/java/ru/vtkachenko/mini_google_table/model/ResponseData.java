package ru.vtkachenko.mini_google_table.model;

import java.util.List;

public class ResponseData {

    private List<Cell> data;

    public ResponseData(List<Cell> data) {
        this.data = data;
    }

    public List<Cell> getData() {
        return data;
    }
}
