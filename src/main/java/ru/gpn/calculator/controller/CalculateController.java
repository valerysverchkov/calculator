package ru.gpn.calculator.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.client.WebServiceClientException;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.service.CalculatorService;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
class CalculateController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculateSync")
    public @ResponseBody CalculateResponse calculateSync(@RequestBody @Valid CalculateRequest calculateRequest) {
        return calculatorService.calculate(calculateRequest);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CalculateResponse handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(constraintViolation -> "fieldName: " + constraintViolation.getPropertyPath().toString() +
                        "; message: " + constraintViolation.getMessage())
                .collect(Collectors.joining());
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setErrorMessage(message);
        return calculateResponse;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CalculateResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "fieldName: " + fieldError.getField() +
                        "; message: " + fieldError.getDefaultMessage())
                .collect(Collectors.joining());
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setErrorMessage(message);
        return calculateResponse;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public CalculateResponse handleInvalidFormatException(InvalidFormatException e) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setErrorMessage("'" + e.getValue().toString() + "' is not valid");
        return calculateResponse;
    }

    @ExceptionHandler(WebServiceClientException.class)
    public CalculateResponse handleWebServiceClientException(WebServiceClientException e) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setErrorMessage("Calculator Web Service returned error: " + e.getLocalizedMessage());
        return calculateResponse;
    }

}
