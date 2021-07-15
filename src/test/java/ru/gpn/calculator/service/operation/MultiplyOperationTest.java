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
import ru.gpn.calculator.model.calculatorsoap.Multiply;
import ru.gpn.calculator.model.calculatorsoap.MultiplyResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MultiplyOperationTest {

    @InjectMocks
    private MultiplyOperation multiplyOperation;

    @Mock
    private CalculatorClient calculatorClient;

    @Captor
    private ArgumentCaptor<Multiply> multiplyArgumentCaptor;

    @Test
    void whenCallCalculateThenCallCalculatorClientWithExpectedParamsAndReturnExpectedResultOperation() {
        final Integer expectedFirstNumber = 1;
        final Integer expectedSecondNumber = 2;
        final Integer expectedResult = 2;

        MultiplyResponse multiplyResponse = new MultiplyResponse();
        multiplyResponse.setMultiplyResult(expectedResult);
        when(calculatorClient.getResultOperation(any(Multiply.class), any()))
                .thenReturn(multiplyResponse);

        CalculateResponse calculate = multiplyOperation.calculate(expectedFirstNumber, expectedSecondNumber);

        verify(calculatorClient).getResultOperation(multiplyArgumentCaptor.capture(), any());
        Multiply actualMultiply = multiplyArgumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstNumber, Integer.valueOf(actualMultiply.getIntA()));
        Assertions.assertEquals(expectedSecondNumber, Integer.valueOf(actualMultiply.getIntB()));

        Assertions.assertEquals(expectedResult, calculate.getResultNumber());
    }

}