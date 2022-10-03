package ru.vtkachenko.mini_google_table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vtkachenko.mini_google_table.exception.ExpressionException;
import ru.vtkachenko.mini_google_table.model.Cell;
import ru.vtkachenko.mini_google_table.model.Error;
import ru.vtkachenko.mini_google_table.model.ResponseData;
import ru.vtkachenko.mini_google_table.service.ExpressionParser;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class Controller {

    private final ExpressionParser parser;

    @Autowired
    public Controller(ExpressionParser parser) {
        this.parser = parser;
    }

    @PostMapping("/cells")
    public ResponseEntity<?> calculateCells(@RequestBody List<Cell> data) {
        System.out.println("Start");
        System.out.println(data);
        try {
            ResponseData responseData = new ResponseData(parser.calculateCells(data));
            return ResponseEntity.status(200).body(responseData);
        } catch (ExpressionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new Error(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }
}
