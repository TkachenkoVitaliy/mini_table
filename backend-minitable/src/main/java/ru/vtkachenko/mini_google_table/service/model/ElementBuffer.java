package ru.vtkachenko.mini_google_table.service.model;

import java.util.List;

public class ElementBuffer {

    private int pos;

    private List<Element> elements;

    public ElementBuffer(List<Element> elements) {
        System.out.println(elements); //todo remove
        this.elements = elements;
    }

    public Element next() {
        return elements.get(pos++);
    }

    public void back() {
        pos--;
    }

    public int getPos() {
        return pos;
    }
}
