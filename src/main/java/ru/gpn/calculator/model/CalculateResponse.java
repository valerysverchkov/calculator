package ru.gpn.calculator.model;

import lombok.Data;

@Data
public class CalculateResponse {

    private Integer resultNumber;
    private String errorMessage;

}
