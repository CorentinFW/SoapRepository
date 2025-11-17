package org.tp1.agence.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
// import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;
import org.tp1.agence.wsdl.hotel.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Client SOAP réel pour communiquer avec l'Hotellerie
 * [DÉSACTIVÉ] Remplacé par MultiHotelSoapClient qui gère plusieurs hôtels
 */
// @Component - DÉSACTIVÉ: Remplacé par MultiHotelSoapClient
public class RealHotelSoapClient extends WebServiceGatewaySupport {

    @Value("${hotel.soap.url}")
    private String hotelSoapUrl;

    @PostConstruct
    public void init() {
        // Configuration du marshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tp1.agence.wsdl.hotel");
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri(hotelSoapUrl);
    }

    /**
     * Recherche des chambres via SOAP
     */
    public List<ChambreDTO> rechercherChambres(RechercheRequest request) {
        try {
            // Créer la requête SOAP
            RechercherChambresRequest soapRequest = new RechercherChambresRequest();
            soapRequest.setAdresse(request.getAdresse());
            soapRequest.setDateArrive(request.getDateArrive());
            soapRequest.setDateDepart(request.getDateDepart());
            soapRequest.setPrixMin(request.getPrixMin());
            soapRequest.setPrixMax(request.getPrixMax());
            soapRequest.setNbrEtoile(request.getNbrEtoile());
            soapRequest.setNbrLits(request.getNbrLits());

            // Appel SOAP
            RechercherChambresResponse soapResponse =
                (RechercherChambresResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(soapRequest);

            // Convertir en DTO
            List<ChambreDTO> chambres = new ArrayList<>();
            if (soapResponse != null && soapResponse.getChambres() != null) {
                for (Chambre chambre : soapResponse.getChambres()) {
                    ChambreDTO dto = new ChambreDTO(
                        chambre.getId(),
                        chambre.getNom(),
                        chambre.getPrix(),
                        chambre.getNbrDeLit(),
                        soapResponse.getHotelNom(),
                        soapResponse.getHotelAdresse()
                    );
                    chambres.add(dto);
                }
            }

            System.out.println("✓ Trouvé " + chambres.size() + " chambre(s) via SOAP");
            return chambres;

        } catch (Exception e) {
            System.err.println("✗ Erreur SOAP lors de la recherche: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Effectue une réservation via SOAP
     */
    public int effectuerReservation(ReservationRequest request) {
        try {
            // Créer la requête SOAP
            EffectuerReservationRequest soapRequest = new EffectuerReservationRequest();

            Client client = new Client();
            client.setNom(request.getClientNom());
            client.setPrenom(request.getClientPrenom());
            client.setNumeroCarteBleue(request.getClientNumeroCarteBleue());

            soapRequest.setClient(client);
            soapRequest.setChambreId(request.getChambreId());
            soapRequest.setDateArrive(request.getDateArrive());
            soapRequest.setDateDepart(request.getDateDepart());

            // Appel SOAP
            EffectuerReservationResponse soapResponse =
                (EffectuerReservationResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(soapRequest);

            if (soapResponse != null && soapResponse.isSuccess()) {
                System.out.println("✓ Réservation effectuée via SOAP: ID=" + soapResponse.getReservationId());
                return soapResponse.getReservationId();
            } else {
                System.err.println("✗ Échec de la réservation: " +
                    (soapResponse != null ? soapResponse.getMessage() : "Pas de réponse"));
                return 0;
            }

        } catch (Exception e) {
            System.err.println("✗ Erreur SOAP lors de la réservation: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}

