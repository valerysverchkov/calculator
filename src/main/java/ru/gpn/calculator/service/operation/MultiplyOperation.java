package ru.gpn.calculator.service.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gpn.calculator.client.CalculatorClient;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.model.calculatorsoap.Multiply;
import ru.gpn.calculator.model.calculatorsoap.MultiplyResponse;

@Service("MULTIPLY")
@RequiredArgsConstructor
class MultiplyOperation implements Operation {

    @Value("${ext.soapAction.multiply}")
    private String soapActionMultiply;

    private final CalculatorClient calculatorClient;

    @Override
    public CalculateResponse calculate(Integer firstNumber, Integer secondNumber) {
        Multiply multiply = getMultiply(firstNumber, secondNumber);
        MultiplyResponse multiplyResponse = (MultiplyResponse) calculatorClient.getResultOperation(multiply, soapActionMultiply);
        return convertResponse(multiplyResponse);
    }

    private static Multiply getMultiply(Integer firstNumber, Integer secondNumber) {
        Multiply multiply = new Multiply();
        multiply.setIntA(firstNumber);
        multiply.setIntB(secondNumber);
        return multiply;
    }

    private static CalculateResponse convertResponse(MultiplyResponse multiplyResponse) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(multiplyResponse.getMultiplyResult());
        return calculateResponse;
    }

}
