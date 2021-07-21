package ru.gpn.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.service.operation.Operation;
import java.util.Map;

@Component
@RequiredArgsConstructor
class CalculatorServiceImpl implements CalculatorService {

    private final Map<String, Operation> operations;
    private final CacheService cacheService;

    @Override
    public CalculateResponse calculate(CalculateRequest request) {

        CalculateResponse resultOperation = cacheService.findResultOperation(request);
        if (resultOperation != null) {
            return resultOperation;
        }

        Operation operation = operations.get(request.getCalculateOperation().name());
        if (operation == null) {
            CalculateResponse calculateResponse = new CalculateResponse();
            calculateResponse.setErrorMessage(request.getCalculateOperation().name() + " is not supported");
            return calculateResponse;
        }

        CalculateResponse response = operation.calculate(request.getFirstNumber(), request.getSecondNumber());
        if (response != null
                && response.getResultNumber() != null) {
            cacheService.saveResultOperation(request, response);
        }

        return response;
    }
}
