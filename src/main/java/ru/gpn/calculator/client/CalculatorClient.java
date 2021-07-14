package ru.gpn.calculator.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CalculatorClient extends WebServiceGatewaySupport {

    public Object getResultOperation(Object request, String soapAction) {
        return getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(soapAction));
    }

}
