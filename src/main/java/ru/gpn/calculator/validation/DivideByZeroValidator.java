package ru.gpn.calculator.validation;

import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.model.CalculateRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DivideByZeroValidator implements ConstraintValidator<DivideByZero, CalculateRequest> {

    private static final Integer ZERO = 0;

    @Override
    public boolean isValid(CalculateRequest value, ConstraintValidatorContext context) {
        return !(CalculateOperation.DIVIDE.equals(value.getCalculateOperation())
                && ZERO.equals(value.getSecondNumber()));
    }

}
