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
import ru.gpn.calculator.model.calculatorsoap.Subtract;
import ru.gpn.calculator.model.calculatorsoap.SubtractResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubtractOperationTest {

    @InjectMocks
    private SubtractOperation subtractOperation;

    @Mock
    private CalculatorClient calculatorClient;

    @Captor
    private ArgumentCaptor<Subtract> subtractArgumentCaptor;

    @Test
    void whenCallCalculateThenCallCalculatorClientWithExpectedParamsAndReturnExpectedResultOperation() {
        final Integer expectedFirstNumber = 1;
        final Integer expectedSecondNumber = 2;
        final Integer expectedResult = 2;

        SubtractResponse subtractResponse = new SubtractResponse();
        subtractResponse.setSubtractResult(expectedResult);
        when(calculatorClient.getResultOperation(any(Subtract.class), any()))
                .thenReturn(subtractResponse);

        CalculateResponse calculate = subtractOperation.calculate(expectedFirstNumber, expectedSecondNumber);

        verify(calculatorClient).getResultOperation(subtractArgumentCaptor.capture(), any());
        Subtract actualSubtract = subtractArgumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstNumber, Integer.valueOf(actualSubtract.getIntA()));
        Assertions.assertEquals(expectedSecondNumber, Integer.valueOf(actualSubtract.getIntB()));

        Assertions.assertEquals(expectedResult, calculate.getResultNumber());
    }

}