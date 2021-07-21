package ru.gpn.calculator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import ru.gpn.calculator.client.CalculatorClient;
import ru.gpn.calculator.model.calculatorsoap.ObjectFactory;

@Configuration
public class CalculatorClientConfiguration {

    @Value("${ext.calculatorUri}")
    private String calculatorUri;

    @Value("${ext.readTimeout}")
    private Integer readTimeout;

    @Value("${ext.connectionTimeout}")
    private Integer connectionTimeout;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(ObjectFactory.class.getPackageName());
        return marshaller;
    }

    @Bean
    public CalculatorClient calculatorClient(Jaxb2Marshaller marshaller) {
        CalculatorClient client = new CalculatorClient();
        client.setDefaultUri(calculatorUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }

    @Bean
    public WebServiceMessageSender messageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setReadTimeout(readTimeout);
        httpComponentsMessageSender.setConnectionTimeout(connectionTimeout);
        return httpComponentsMessageSender;
    }

}
