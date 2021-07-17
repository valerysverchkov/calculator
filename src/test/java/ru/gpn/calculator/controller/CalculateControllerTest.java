package ru.gpn.calculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gpn.calculator.model.CalculateOperation;
import ru.gpn.calculator.model.CalculateRequest;
import ru.gpn.calculator.model.CalculateResponse;
import ru.gpn.calculator.service.CalculatorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CalculateControllerTest {

    private static final String URL = "/calculateSync";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    void whenCallSyncThenRequestCalculatorServiceAndReturnExpectedResponse() throws Exception {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setCalculateOperation(CalculateOperation.ADD);
        calculateRequest.setFirstNumber(1);
        calculateRequest.setSecondNumber(2);

        CalculateResponse calculateResponse = new CalculateResponse();
        final Integer expectedResult = 3;
        calculateResponse.setResultNumber(expectedResult);

        when(calculatorService.calculate(calculateRequest))
                .thenReturn(calculateResponse);

        mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(calculateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultNumber").value(expectedResult));
    }

    @Test
    void whenCallSyncWithNotValidRequestThenHandledValidationExceptionAndReturnBadRequestWithErrorMessage() throws Exception {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setCalculateOperation(CalculateOperation.ADD);
        calculateRequest.setFirstNumber(null);
        calculateRequest.setSecondNumber(2);

        String expectedResult = "fieldName: firstNumber; message: firstNumber cannot be null";
        mockMvc.perform(post(URL)
                .content(objectMapper.writeValueAsString(calculateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(expectedResult));
    }

}