package ru.gpn.calculator.model;

import lombok.Data;
import ru.gpn.calculator.validation.ValueOfEnum;
import javax.validation.constraints.NotNull;

@Data
public class CalculateRequest {

    @NotNull
    @ValueOfEnum(enumClass = CalculateOperation.class)
    private CalculateOperation calculateOperation;

    @NotNull
    private Integer firstNumber;

    @NotNull
    private Integer secondNumber;

}
