package org.tp1.agence.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;
import org.tp1.agence.wsdl.hotel.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Client SOAP qui interroge plusieurs hôtels
 */
@Component
public class MultiHotelSoapClient extends WebServiceGatewaySupport {

    @Value("${hotel.soap.urls}")
    private String hotelSoapUrls;

    private List<String> hotelUrls = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Configuration du marshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tp1.agence.wsdl.hotel");
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        // Parser les URLs
        if (hotelSoapUrls != null && !hotelSoapUrls.isEmpty()) {
            String[] urls = hotelSoapUrls.split(",");
            for (String url : urls) {
                String trimmedUrl = url.trim();
                if (!trimmedUrl.isEmpty()) {
                    hotelUrls.add(trimmedUrl);
                }
            }
        }

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Agence - Configuration SOAP");
        System.out.println("  Nombre d'hôtels: " + hotelUrls.size());
        for (int i = 0; i < hotelUrls.size(); i++) {
            System.out.println("  Hotel " + (i+1) + ": " + hotelUrls.get(i));
        }
        System.out.println("═══════════════════════════════════════════");
    }

    /**
     * Recherche des chambres via SOAP dans tous les hôtels
     */
    public List<ChambreDTO> rechercherChambres(RechercheRequest request) {
        List<ChambreDTO> toutesLesChambres = new ArrayList<>();

        for (String hotelUrl : hotelUrls) {
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

                // Définir l'URL cible
                setDefaultUri(hotelUrl);

                // Appel SOAP
                RechercherChambresResponse soapResponse =
                    (RechercherChambresResponse) getWebServiceTemplate()
                        .marshalSendAndReceive(soapRequest);

                // Convertir en DTO
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
                        toutesLesChambres.add(dto);
                    }
                    System.out.println("✓ [" + hotelUrl + "] Trouvé " + soapResponse.getChambres().size() + " chambre(s)");
                }

            } catch (Exception e) {
                System.err.println("✗ [" + hotelUrl + "] Erreur: " + e.getMessage());
            }
        }

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Total: " + toutesLesChambres.size() + " chambre(s) trouvée(s)");
        System.out.println("═══════════════════════════════════════════");

        return toutesLesChambres;
    }

    /**
     * Effectue une réservation via SOAP
     * Note: Pour l'instant, utilise le premier hôtel
     * TODO: Améliorer pour cibler le bon hôtel selon l'ID de chambre
     */
    public int effectuerReservation(ReservationRequest request) {
        if (hotelUrls.isEmpty()) {
            System.err.println("✗ Aucun hôtel configuré");
            return 0;
        }

        // Pour simplifier, on essaie tous les hôtels jusqu'à trouver celui qui a la chambre
        for (String hotelUrl : hotelUrls) {
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

                // Définir l'URL cible
                setDefaultUri(hotelUrl);

                // Appel SOAP
                EffectuerReservationResponse soapResponse =
                    (EffectuerReservationResponse) getWebServiceTemplate()
                        .marshalSendAndReceive(soapRequest);

                if (soapResponse != null && soapResponse.isSuccess()) {
                    System.out.println("✓ [" + hotelUrl + "] Réservation effectuée: ID=" + soapResponse.getReservationId());
                    return soapResponse.getReservationId();
                }

            } catch (Exception e) {
                // Continuer avec le prochain hôtel
                System.err.println("✗ [" + hotelUrl + "] Erreur: " + e.getMessage());
            }
        }

        System.err.println("✗ Échec de la réservation sur tous les hôtels");
        return 0;
    }
}

