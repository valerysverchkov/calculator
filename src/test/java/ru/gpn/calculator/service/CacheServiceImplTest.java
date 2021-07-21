package ru.gpn.calculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gpn.calculator.model.CalculateCache;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.repository.CalculateCacheRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {

    private static final Integer FIRST_NUMBER = 1;
    private static final Integer SECOND_NUMBER = 2;
    private static final Integer RESULT_NUMBER = 3;
    private static final CalculateOperation OPERATION = CalculateOperation.ADD;

    @InjectMocks
    private CacheServiceImpl cacheService;

    @Mock
    private CalculateCacheRepository cacheRepository;

    @Test
    void whenCallFindResultOperationWithCalculateRequestThenSearchingResultInCacheRepositoryAndReturnCalculateResponse() {
        when(cacheRepository.findByOperationAndFirstNumberAndSecondNumber(OPERATION.name(), FIRST_NUMBER, SECOND_NUMBER))
                .thenReturn(getCalculateCache());

        CalculateResponse resultOperation = cacheService.findResultOperation(getCalculateRequest());

        Assertions.assertEquals(RESULT_NUMBER, resultOperation.getResultNumber());
    }

    @Test
    void whenCallFindResultOperationWithCommutativeOperationThenSearchingResultInCacheByEachNumberAndReturnCalculateResponse() {
        when(cacheRepository.findByOperationAndFirstNumberAndSecondNumber(OPERATION.name(), FIRST_NUMBER, SECOND_NUMBER))
                .thenReturn(null);
        when(cacheRepository.findByOperationAndFirstNumberAndSecondNumber(OPERATION.name(), SECOND_NUMBER, FIRST_NUMBER))
                .thenReturn(getCalculateCache());

        CalculateResponse resultOperation = cacheService.findResultOperation(getCalculateRequest());

        Assertions.assertEquals(RESULT_NUMBER, resultOperation.getResultNumber());
    }

    @Test
    void whenCallSaveResultOperationWithCalculateRequestAndResponseThenSavingResultInCache() {
        cacheService.saveResultOperation(getCalculateRequest(), getCalculateResponse());

        verify(cacheRepository).save(getCalculateCache());
    }

    private static CalculateCache getCalculateCache() {
        CalculateCache calculateCache = new CalculateCache();
        calculateCache.setOperation(OPERATION.name());
        calculateCache.setFirstNumber(FIRST_NUMBER);
        calculateCache.setSecondNumber(SECOND_NUMBER);
        calculateCache.setResultNumber(RESULT_NUMBER);
        return calculateCache;
    }

    private static CalculateRequest getCalculateRequest() {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setCalculateOperation(OPERATION);
        calculateRequest.setFirstNumber(FIRST_NUMBER);
        calculateRequest.setSecondNumber(SECOND_NUMBER);
        return calculateRequest;
    }

    private static CalculateResponse getCalculateResponse() {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setResultNumber(RESULT_NUMBER);
        return calculateResponse;
    }

}