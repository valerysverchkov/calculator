package ru.gpn.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.gpn.calculator.model.CalculateCache;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.repository.CalculateCacheRepository;

@Testcontainers
@SpringBootTest
class RedisCacheIntTest {

    private static final int REDIS_PORT = 6379;
    private static final String OPERATION_NAME = CalculateOperation.ADD.name();
    private static final Integer FIRST_NUMBER = 1;
    private static final Integer SECOND_NUMBER = 2;
    private static final Integer RESULT_NUMBER = 3;

    @Container
    private static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.4-alpine"))
            .withExposedPorts(REDIS_PORT);

    @DynamicPropertySource
    static void registerRedisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", () -> redis.getContainerIpAddress());
        registry.add("spring.redis.port", () -> redis.getFirstMappedPort());
    }

    @Autowired
    private CalculateCacheRepository cacheRepository;

    @Test
    void whenSaveCalculateResultInRedisCacheThenFindReturnsSavedResult() {
        CalculateCache calculateCache = getCalculateCache();

        cacheRepository.save(calculateCache);

        CalculateCache calculateCacheResult = cacheRepository
                .findByOperationAndFirstNumberAndSecondNumber(OPERATION_NAME, FIRST_NUMBER, SECOND_NUMBER);

        Assertions.assertNotNull(calculateCacheResult.getId(), "After saving in the cache, 'Id' must be initialized");
        Assertions.assertEquals(calculateCache.getOperation(), calculateCacheResult.getOperation());
        Assertions.assertEquals(calculateCache.getFirstNumber(), calculateCacheResult.getFirstNumber());
        Assertions.assertEquals(calculateCache.getSecondNumber(), calculateCacheResult.getSecondNumber());
        Assertions.assertEquals(calculateCache.getResultNumber(), calculateCacheResult.getResultNumber());
    }

    private static CalculateCache getCalculateCache() {
        CalculateCache calculateCache = new CalculateCache();
        calculateCache.setOperation(OPERATION_NAME);
        calculateCache.setFirstNumber(FIRST_NUMBER);
        calculateCache.setSecondNumber(SECOND_NUMBER);
        calculateCache.setResultNumber(RESULT_NUMBER);
        return calculateCache;
    }

}
