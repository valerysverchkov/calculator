package ru.gpn.calculator.service;

import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;

public interface CalculatorService {

    CalculateResponse calculate(CalculateRequest request);

}
