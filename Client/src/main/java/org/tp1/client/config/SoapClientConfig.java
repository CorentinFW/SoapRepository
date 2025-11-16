package org.tp1.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.tp1.client.soap.AgenceSoapClient;

/**
 * Configuration du client SOAP
 */
@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tp1.client.wsdl.agence");
        return marshaller;
    }

    @Bean
    public AgenceSoapClient agenceSoapClient(Jaxb2Marshaller marshaller) {
        AgenceSoapClient client = new AgenceSoapClient();
        client.setDefaultUri("http://localhost:8081/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}

