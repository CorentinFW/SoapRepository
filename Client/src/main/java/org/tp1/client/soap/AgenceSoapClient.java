package org.tp1.client.soap;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tp1.client.wsdl.agence.*;

/**
 * Client SOAP pour communiquer avec l'Agence
 */
public class AgenceSoapClient extends WebServiceGatewaySupport {

    /**
     * Test de connexion à l'agence
     */
    public String ping() {
        PingRequest request = new PingRequest();

        PingResponse response = (PingResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response.getMessage();
    }

    /**
     * Rechercher des chambres disponibles
     */
    public RechercherChambresResponse rechercherChambres(
            String adresse,
            String dateArrive,
            String dateDepart,
            Float prixMin,
            Float prixMax,
            Integer nbrEtoile,
            Integer nbrLits) {

        RechercherChambresRequest request = new RechercherChambresRequest();
        request.setAdresse(adresse);
        request.setDateArrive(dateArrive);
        request.setDateDepart(dateDepart);
        request.setPrixMin(prixMin);
        request.setPrixMax(prixMax);
        request.setNbrEtoile(nbrEtoile);
        request.setNbrLits(nbrLits);

        return (RechercherChambresResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }

    /**
     * Effectuer une réservation
     */
    public EffectuerReservationResponse effectuerReservation(
            String nom,
            String prenom,
            String numeroCarteBleue,
            int chambreId,
            String dateArrive,
            String dateDepart) {

        EffectuerReservationRequest request = new EffectuerReservationRequest();

        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setNumeroCarteBleue(numeroCarteBleue);

        request.setClient(client);
        request.setChambreId(chambreId);
        request.setDateArrive(dateArrive);
        request.setDateDepart(dateDepart);

        return (EffectuerReservationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }
}

