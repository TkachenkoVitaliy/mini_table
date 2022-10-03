package ru.vtkachenko.mini_google_table.service.model;

public class Element {
    private ElementType type;
    private String value;

    public Element(ElementType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Element(ElementType type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Element{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
