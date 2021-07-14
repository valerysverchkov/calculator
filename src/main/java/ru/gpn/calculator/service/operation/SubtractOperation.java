package ru.gpn.calculator.service.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gpn.calculator.client.CalculatorClient;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.model.calculatorsoap.Subtract;
import ru.gpn.calculator.model.calculatorsoap.SubtractResponse;

@Service("SUBTRACT")
@RequiredArgsConstructor
class SubtractOperation implements Operation {

    @Value("${ext.soapAction.subtract}")
    private String soapActionSubtract;

    private final CalculatorClient calculatorClient;

    @Override
    public CalculateResponse calculate(Integer firstNumber, Integer secondNumber) {
        Subtract subtract = getSubtract(firstNumber, secondNumber);
        SubtractResponse subtractResponse = (SubtractResponse) calculatorClient.getResultOperation(subtract, soapActionSubtract);
        return convertResponse(subtractResponse);
    }

    private static Subtract getSubtract(Integer firstNumber, Integer secondNumber) {
        Subtract subtract = new Subtract();
        subtract.setIntA(firstNumber);
        subtract.setIntB(secondNumber);
        return subtract;
    }

    private static CalculateResponse convertResponse(SubtractResponse subtractResponse) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(subtractResponse.getSubtractResult());
        return calculateResponse;
    }

}
