package ru.gpn.calculator.model;

import lombok.Data;

@Data
public class CalculateRequest {

    private CalculateOperation calculateOperation;

    private Integer firstNumber;

    private Integer secondNumber;

}
