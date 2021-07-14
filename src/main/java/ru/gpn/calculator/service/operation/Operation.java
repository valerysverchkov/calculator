package ru.gpn.calculator.service.operation;

import ru.gpn.calculator.model.CalculateResponse;

public interface Operation {

    CalculateResponse calculate(Integer firstNumber, Integer secondNumber);

}
