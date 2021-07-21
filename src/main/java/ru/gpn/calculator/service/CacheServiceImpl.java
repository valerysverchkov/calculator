package ru.gpn.calculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.gpn.calculator.model.CalculateCache;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.repository.CalculateCacheRepository;

@Component
@Slf4j
@RequiredArgsConstructor
class CacheServiceImpl implements CacheService {

    private final CalculateCacheRepository cacheRepository;

    @Override
    public CalculateResponse findResultOperation(CalculateRequest calculateRequest) {
        CalculateCache calculateCache = cacheRepository.findByOperationAndFirstNumberAndSecondNumber(
                calculateRequest.getCalculateOperation().name(),
                calculateRequest.getFirstNumber(),
                calculateRequest.getSecondNumber()
        );
        if (calculateCache == null
                && isOperationCommutative(calculateRequest.getCalculateOperation())) {
            calculateCache = cacheRepository.findByOperationAndFirstNumberAndSecondNumber(
                    calculateRequest.getCalculateOperation().name(),
                    calculateRequest.getSecondNumber(),
                    calculateRequest.getFirstNumber()
            );
        }
        if (calculateCache != null) {
            CalculateResponse calculateResponse = new CalculateResponse();
            calculateResponse.setResultNumber(calculateCache.getResultNumber());
            log.info("Operation found in cache with ID={}", calculateCache.getId());
            return calculateResponse;
        }
        return null;
    }

    @Override
    public void saveResultOperation(CalculateRequest calculateRequest, CalculateResponse calculateResponse) {
        CalculateCache calculateCache = new CalculateCache();
        calculateCache.setOperation(calculateRequest.getCalculateOperation().name());
        calculateCache.setFirstNumber(calculateRequest.getFirstNumber());
        calculateCache.setSecondNumber(calculateRequest.getSecondNumber());
        calculateCache.setResultNumber(calculateResponse.getResultNumber());

        cacheRepository.save(calculateCache);
        log.info("Operation saved in cache with ID={}", calculateCache.getId());
    }

    private static boolean isOperationCommutative(CalculateOperation calculateOperation) {
        return CalculateOperation.ADD.equals(calculateOperation)
                || CalculateOperation.MULTIPLY.equals(calculateOperation);
    }

}
