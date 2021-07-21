package ru.gpn.calculator.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalculateResponse implements Serializable {

    private Integer resultNumber;
    private String errorMessage;

}
