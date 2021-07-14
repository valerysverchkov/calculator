package ru.gpn.calculator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.gpn.calculator.client.CalculatorClient;

@Configuration
public class CalculatorClientConfiguration {

    @Value("${ext.calculatorUri}")
    private String calculatorUri;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.gpn.calculator.model.calculatorsoap");
        return marshaller;
    }

    @Bean
    public CalculatorClient calculatorClient(Jaxb2Marshaller marshaller) {
        CalculatorClient client = new CalculatorClient();
        client.setDefaultUri(calculatorUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
