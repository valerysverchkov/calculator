package ru.gpn.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.service.operation.Operation;

import java.util.Map;

@SpringBootTest
class CalculatorApplicationTests {

    @Autowired
    private Map<String, Operation> operations;

    @Test
    void contextLoads() {
    }

    @Test
    void whenLoadContextThenLoadAllOperations() {
        Assertions.assertEquals(CalculateOperation.values().length, operations.size(),
                "Count of CalculateOperation enum does not correspond to count of implemented");
        for (CalculateOperation calculateOperation : CalculateOperation.values()) {
            Operation operation = operations.get(calculateOperation.name());
            Assertions.assertNotNull(operation,
                    "No implementation found for CalculateOperation." + calculateOperation.name());
        }
    }

}
