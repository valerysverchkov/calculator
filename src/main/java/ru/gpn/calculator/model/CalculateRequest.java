package ru.gpn.calculator.model;

import lombok.Data;
import ru.gpn.calculator.validation.DivideByZero;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@DivideByZero
public class CalculateRequest implements Serializable {

    @NotNull(message = "calculateOperation cannot be null")
    private CalculateOperation calculateOperation;

    @NotNull(message = "firstNumber cannot be null")
    @Max(Integer.MAX_VALUE)
    private Integer firstNumber;

    @NotNull(message = "secondNumber cannot be null")
    @Max(Integer.MAX_VALUE)
    private Integer secondNumber;

}
