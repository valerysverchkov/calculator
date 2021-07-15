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
import ru.gpn.calculator.model.calculatorsoap.Divide;
import ru.gpn.calculator.model.calculatorsoap.DivideResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DivideOperationTest {

    @InjectMocks
    private DivideOperation divideOperation;

    @Mock
    private CalculatorClient calculatorClient;

    @Captor
    private ArgumentCaptor<Divide> divideArgumentCaptor;

    @Test
    void whenCallCalculateThenCallCalculatorClientWithExpectedParamsAndReturnExpectedResultOperation() {
        final Integer expectedFirstNumber = 1;
        final Integer expectedSecondNumber = 2;
        final Integer expectedResult = 2;

        DivideResponse divideResponse = new DivideResponse();
        divideResponse.setDivideResult(expectedResult);
        when(calculatorClient.getResultOperation(any(Divide.class), any()))
                .thenReturn(divideResponse);

        CalculateResponse calculate = divideOperation.calculate(expectedFirstNumber, expectedSecondNumber);

        verify(calculatorClient).getResultOperation(divideArgumentCaptor.capture(), any());
        Divide actualDivide = divideArgumentCaptor.getValue();
        Assertions.assertEquals(expectedFirstNumber, Integer.valueOf(actualDivide.getIntA()));
        Assertions.assertEquals(expectedSecondNumber, Integer.valueOf(actualDivide.getIntB()));

        Assertions.assertEquals(expectedResult, calculate.getResultNumber());
    }

}