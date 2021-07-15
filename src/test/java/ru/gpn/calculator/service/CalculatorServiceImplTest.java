package ru.gpn.calculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.service.operation.Operation;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorServiceImplTest {

    private static final CalculateOperation MOCK_OPERATION = CalculateOperation.ADD;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @Mock
    private Map<String, Operation> operations;

    @Mock
    private Operation operation;

    @Test
    void whenCallCalculateThenCallCalculatorClientWithExpectedParamsAndReturnExpectedResultOperation() {
        final Integer expectedFirstNumber = 1;
        final Integer expectedSecondNumber = 2;
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setCalculateOperation(MOCK_OPERATION);
        calculateRequest.setFirstNumber(expectedFirstNumber);
        calculateRequest.setSecondNumber(expectedSecondNumber);

        when(operations.get(MOCK_OPERATION.name()))
                .thenReturn(operation);

        final Integer expectedResultNumber = 2;
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(expectedResultNumber);
        when(operation.calculate(expectedFirstNumber, expectedSecondNumber))
                .thenReturn(calculateResponse);

        CalculateResponse actualResult = calculatorService.calculate(calculateRequest);

        Assertions.assertEquals(expectedResultNumber, actualResult.getResultNumber());
    }

}