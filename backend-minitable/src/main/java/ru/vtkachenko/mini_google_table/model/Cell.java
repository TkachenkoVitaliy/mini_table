package ru.vtkachenko.mini_google_table.model;

public class Cell {
    private String address;
    private String value;

    public Cell(String address, String value) {
        this.address = address;
        this.value = value;
    }

    public Cell() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "address='" + address + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
