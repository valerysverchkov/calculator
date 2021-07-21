package ru.gpn.calculator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("CalculateCache")
@Data
public class CalculateCache implements Serializable {

    @Id
    private String id;
    @Indexed
    private String operation;
    @Indexed
    private Integer firstNumber;
    @Indexed
    private Integer secondNumber;
    private Integer resultNumber;

}
