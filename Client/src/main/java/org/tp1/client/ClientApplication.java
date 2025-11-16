package org.tp1.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.tp1.client.cli.ClientCLISoap;

/**
 * Application principale du client
 * Lance l'interface CLI pour communiquer avec l'agence via SOAP
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        // Démarrer Spring Boot mais sans serveur web
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);

        // Récupérer le CLI SOAP et le démarrer
        ClientCLISoap cli = context.getBean(ClientCLISoap.class);
        cli.run();

        // Fermer le contexte Spring après la fin du CLI
        System.exit(SpringApplication.exit(context));
    }
}

