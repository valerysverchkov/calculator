package ru.gpn.calculator.service.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gpn.calculator.client.CalculatorClient;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.model.calculatorsoap.Divide;
import ru.gpn.calculator.model.calculatorsoap.DivideResponse;

@Service("DIVIDE")
@RequiredArgsConstructor
class DivideOperation implements Operation {

    @Value("${ext.soapAction.divide}")
    private String soapActionDivide;

    private final CalculatorClient calculatorClient;

    @Override
    public CalculateResponse calculate(Integer firstNumber, Integer secondNumber) {
        Divide divide = getDivide(firstNumber, secondNumber);
        DivideResponse divideResponse = (DivideResponse) calculatorClient.getResultOperation(divide, soapActionDivide);
        return convertResponse(divideResponse);
    }

    private static Divide getDivide(Integer firstNumber, Integer secondNumber) {
        Divide divide = new Divide();
        divide.setIntA(firstNumber);
        divide.setIntB(secondNumber);
        return divide;
    }

    private static CalculateResponse convertResponse(DivideResponse divideResponse) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(divideResponse.getDivideResult());
        return calculateResponse;
    }

}
