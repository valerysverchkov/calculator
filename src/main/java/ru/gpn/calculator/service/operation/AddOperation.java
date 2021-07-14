package ru.gpn.calculator.service.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.model.calculatorsoap.Add;
import ru.gpn.calculator.model.calculatorsoap.AddResponse;
import ru.gpn.calculator.client.CalculatorClient;

@Service("ADD")
@RequiredArgsConstructor
class AddOperation implements Operation {

    @Value("${ext.soapAction.add}")
    private String soapActionAdd;

    private final CalculatorClient calculatorClient;

    @Override
    public CalculateResponse calculate(Integer firstNumber, Integer secondNumber) {
        Add add = getAdd(firstNumber, secondNumber);
        AddResponse addResponse = (AddResponse) calculatorClient.getResultOperation(add, soapActionAdd);
        return convertResponse(addResponse);
    }

    private static Add getAdd(Integer firstNumber, Integer secondNumber) {
        Add add = new Add();
        add.setIntA(firstNumber);
        add.setIntB(secondNumber);
        return add;
    }

    private static CalculateResponse convertResponse(AddResponse addResponse) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(addResponse.getAddResult());
        return calculateResponse;
    }

}
