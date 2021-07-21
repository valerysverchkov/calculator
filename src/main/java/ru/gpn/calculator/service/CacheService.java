package ru.gpn.calculator.service;

import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;

public interface CacheService {

    CalculateResponse findResultOperation(CalculateRequest calculateRequest);

    void saveResultOperation(CalculateRequest calculateRequest, CalculateResponse calculateResponse);

}
