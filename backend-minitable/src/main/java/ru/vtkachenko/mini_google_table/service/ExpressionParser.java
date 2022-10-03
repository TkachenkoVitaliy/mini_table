package ru.vtkachenko.mini_google_table.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vtkachenko.mini_google_table.exception.ExpressionException;
import ru.vtkachenko.mini_google_table.model.Cell;
import ru.vtkachenko.mini_google_table.service.model.Element;
import ru.vtkachenko.mini_google_table.service.model.ElementBuffer;
import ru.vtkachenko.mini_google_table.service.model.ElementType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExpressionParser {

    private List<Cell> data = new ArrayList<>();
    private Set<Cell> calculatedCells = new HashSet<>();

    @Value("${table.columns}")
    private int columns = 4; //todo remove init value

    @Value("${table.rows}")
    private int rows = 4; //todo remove init value

    public List<Cell> calculateCells(List<Cell> input) throws ExpressionException {
        data = input;
        for(Cell cell : data) {
            if(!calculatedCells.contains(cell)) {
                calculateCell(cell);
            }
        }
        return data;
    }

    private Cell calculateCell(Cell cell) throws ExpressionException {
        String value = cell.getValue();
        if(value.startsWith("=") && value.length() > 2) {
            String valueWithOutEqualSymbol = value.substring(1);
            try {
                BigDecimal result = calculate(valueWithOutEqualSymbol);
                cell.setValue(result.toString());
                calculatedCells.add(cell);
            } catch (ExpressionException e) {
                e.cellAddress = cell.getAddress();
                throw e;
            }

        }
        if(value.equals("")) cell.setValue("0");
        return cell;
    }

    public BigDecimal calculate(String expression) throws ExpressionException {
        if(expression.startsWith("=")) expression = expression.substring(1);
        ElementBuffer elements = new ElementBuffer(analyze(expression));
        return plusMinus(elements);
    }

    public BigDecimal plusMinus(ElementBuffer elements) throws ExpressionException {
        BigDecimal value = mulDiv(elements);
        while (true) {
            Element element = elements.next();
            switch (element.getType()) {
                case OP_PLUS:
                    value = value.add(mulDiv(elements));
                    break;
                case OP_MINUS:
                    value = value.subtract(mulDiv(elements));
                    break;
                case EOF:
                case RIGHT_BRACKET:
                    elements.back();
                    return value;
                default:
                    throw new ExpressionException("Unexpected token: " + element.getValue());
            }
        }
    }

    public BigDecimal mulDiv(ElementBuffer elements) throws ExpressionException {
        BigDecimal value = factor(elements);
        while (true) {
            Element element = elements.next();
            switch (element.getType()) {
                case OP_MUL:
                    value = value.multiply(factor(elements));
                    break;
                case OP_DIV:
                    try {
                        value = value.divide(factor(elements), 6, RoundingMode.HALF_UP).stripTrailingZeros();
                    } catch (ArithmeticException e) {
                        throw new ExpressionException(e.getMessage());
                    }
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    elements.back();
                    return value;
                default:
                    throw new ExpressionException("Unexpected token: " + element.getValue());
            }
        }
    }

    public BigDecimal factor(ElementBuffer elements) throws ExpressionException {
        Element element = elements.next();
        switch (element.getType()) {
            case OP_MINUS:
                BigDecimal preValue = factor(elements);
                return new BigDecimal("-" + preValue.toString());
            case REFERENCE:
                String reference = element.getValue();
                Cell referencedCell = data.stream()
                        .filter(cell -> cell.getAddress().equals(reference))
                        .findFirst().orElseThrow(() -> new ExpressionException("Wrong reference - " + reference));
                try {
                    return new BigDecimal(calculateCell(referencedCell).getValue());
                } catch (NumberFormatException e) {
                    throw new ExpressionException("Can't parse number from cell " + referencedCell.getAddress());
                }
            case NUMBER:
                return new BigDecimal(element.getValue());
            case LEFT_BRACKET:
                BigDecimal value = plusMinus(elements);
                element = elements.next();
                if(element.getType() != ElementType.RIGHT_BRACKET) {
                    throw new ExpressionException("Unexpected token: " + element.getValue());
                }
                return value;
            default:
                throw new ExpressionException("Unexpected token: " + element.getValue());
        }
    }

    public List<Element> analyze(String expression) throws ExpressionException {
        List<Element> elements = new ArrayList<>();
        int pos = 0;

        while (pos < expression.length()) {
            char c = expression.charAt(pos);
            switch (c) {
                case '(':
                    elements.add(new Element(ElementType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    elements.add(new Element(ElementType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    elements.add(new Element(ElementType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    elements.add(new Element(ElementType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    elements.add(new Element(ElementType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    elements.add(new Element(ElementType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if(c >= '0' && c <= '9' || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if(pos >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(pos);
                        } while (c >= '0' && c <= '9' || c == '.');
                        String value = sb.toString();
                        if(value.charAt(0) == '.' || value.charAt(value.length()-1) == '.') {
                            throw new ExpressionException(value + " - isn't correct numeric value");
                        }
                        elements.add(new Element(ElementType.NUMBER, value));
                    } else {
                        if (c!= ' ') {
                            if (c >= 'A' && c < ('A' + columns)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(c);
                                pos++;
                                if (pos < expression.length()) {
                                    c = expression.charAt(pos);
                                    if(c >= '1' && c < ('1' + rows)) {
                                        sb.append(c);
                                        pos++;
                                        elements.add(new Element(ElementType.REFERENCE, sb.toString()));
                                    } else {
                                        throw new ExpressionException("Unexpected token: " + c);
                                    }
                                } else {
                                    throw new ExpressionException("Unexpected token: " + c);
                                }
                            } else {
                                throw new ExpressionException("Unexpected token: " + c);
                            }
                        } else {
                            pos++;
                        }
                    }
            }
        }
        elements.add(new Element(ElementType.EOF, "EOF"));
        return elements;
    }
}
