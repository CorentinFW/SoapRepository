//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.11.16 à 02:13:24 PM CET 
//


package org.tp1.client.wsdl.agence;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tp1.client.wsdl.agence package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tp1.client.wsdl.agence
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PingRequest }
     * 
     */
    public PingRequest createPingRequest() {
        return new PingRequest();
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link RechercherChambresRequest }
     * 
     */
    public RechercherChambresRequest createRechercherChambresRequest() {
        return new RechercherChambresRequest();
    }

    /**
     * Create an instance of {@link RechercherChambresResponse }
     * 
     */
    public RechercherChambresResponse createRechercherChambresResponse() {
        return new RechercherChambresResponse();
    }

    /**
     * Create an instance of {@link Chambre }
     * 
     */
    public Chambre createChambre() {
        return new Chambre();
    }

    /**
     * Create an instance of {@link EffectuerReservationRequest }
     * 
     */
    public EffectuerReservationRequest createEffectuerReservationRequest() {
        return new EffectuerReservationRequest();
    }

    /**
     * Create an instance of {@link Client }
     * 
     */
    public Client createClient() {
        return new Client();
    }

    /**
     * Create an instance of {@link EffectuerReservationResponse }
     * 
     */
    public EffectuerReservationResponse createEffectuerReservationResponse() {
        return new EffectuerReservationResponse();
    }

}
