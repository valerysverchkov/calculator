package ru.gpn.calculator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DivideByZeroValidator.class)
public @interface DivideByZero {

    String message() default "Division by zero is not supported";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
