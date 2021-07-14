package ru.gpn.calculator.service.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gpn.calculator.client.CalculatorClient;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.model.calculatorsoap.Add;
import ru.gpn.calculator.model.calculatorsoap.AddResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddOperationTest {

    @InjectMocks
    private AddOperation addOpertion;

    @Mock
    private CalculatorClient calculatorClient;

    @Captor
    private ArgumentCaptor<Add> addArgumentCaptor;

    @Test
    public void whenCallCalculateThenCallCalculatorClientWithExpectedParamsAndReturnExpectedResultOperation(){
        final Integer expectedFirstNumber = 1;
        final Integer expectedSecondNumber = 2;
        final Integer expectedResult = 2;

        AddResponse addResponse = new AddResponse();
        addResponse.setAddResult(expectedResult);
        when(calculatorClient.getResultOperation(any(Add.class), any()))
                .thenReturn(addResponse);

        CalculateResponse calculate = addOpertion.calculate(expectedFirstNumber, expectedSecondNumber);

        verify(calculatorClient).getResultOperation(addArgumentCaptor.capture(), any());
        Add actualAdd = addArgumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstNumber, Integer.valueOf(actualAdd.getIntA()));
        Assertions.assertEquals(expectedSecondNumber, Integer.valueOf(actualAdd.getIntB()));

        Assertions.assertEquals(expectedResult, calculate.getResultNumber());
    }

}