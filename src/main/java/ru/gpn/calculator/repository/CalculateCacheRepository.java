package ru.gpn.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gpn.calculator.model.CalculateCache;

public interface CalculateCacheRepository extends CrudRepository<CalculateCache, Long> {

    CalculateCache findByOperationAndFirstNumberAndSecondNumber(String operation, Integer firstNumber, Integer secondNumber);

}
