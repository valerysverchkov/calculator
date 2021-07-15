package ru.gpn.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.service.CalculatorService;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CalculateController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculateSync")
    public @ResponseBody CalculateResponse calculateSync(@RequestBody @Valid CalculateRequest calculateRequest) {
        return calculatorService.calculate(calculateRequest);
    }
}
