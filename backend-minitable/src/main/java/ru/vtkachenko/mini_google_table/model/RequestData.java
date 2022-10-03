package ru.vtkachenko.mini_google_table.model;

import java.util.List;

public class RequestData {
    private List<Cell> data;

    public List<Cell> getData() {
        return data;
    }

    public void setData(List<Cell> data) {
        this.data = data;
    }
}
