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
                            soapResponse.getHotelAdresse(),
                            chambre.getImageUrl()
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

    /**
     * Récupère toutes les réservations de tous les hôtels
     * @return Map avec nom de l'hôtel comme clé et liste de réservations comme valeur
     */
    public java.util.Map<String, List<Reservation>> getAllReservationsByHotel() {
        java.util.Map<String, List<Reservation>> reservationsParHotel = new java.util.LinkedHashMap<>();

        for (String hotelUrl : hotelUrls) {
            try {
                // Créer la requête SOAP
                GetReservationsRequest soapRequest = new GetReservationsRequest();

                // Définir l'URL cible
                setDefaultUri(hotelUrl);

                // Appel SOAP
                GetReservationsResponse soapResponse =
                    (GetReservationsResponse) getWebServiceTemplate()
                        .marshalSendAndReceive(soapRequest);

                if (soapResponse != null) {
                    // Récupérer le nom de l'hôtel
                    String hotelNom = getHotelName(hotelUrl);

                    List<Reservation> reservations = soapResponse.getReservations();
                    reservationsParHotel.put(hotelNom, reservations);

                    System.out.println("✓ [" + hotelNom + "] " + reservations.size() + " réservation(s)");
                }

            } catch (Exception e) {
                System.err.println("✗ [" + hotelUrl + "] Erreur: " + e.getMessage());
                // Ajouter une liste vide en cas d'erreur
                reservationsParHotel.put(getHotelName(hotelUrl), new ArrayList<>());
            }
        }

        return reservationsParHotel;
    }

    /**
     * Extrait le nom de l'hôtel à partir de l'URL ou récupère depuis l'hôtel
     */
    private String getHotelName(String hotelUrl) {
        try {
            // Essayer de récupérer les infos de l'hôtel
            GetHotelInfoRequest request = new GetHotelInfoRequest();
            setDefaultUri(hotelUrl);

            GetHotelInfoResponse response = (GetHotelInfoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

            if (response != null && response.getHotel() != null) {
                return response.getHotel().getNom();
            }
        } catch (Exception e) {
            // Si erreur, déduire du port
        }

        // Fallback: déduire du port
        if (hotelUrl.contains("8082")) return "Paris";
        if (hotelUrl.contains("8083")) return "Lyon";
        if (hotelUrl.contains("8084")) return "Montpellier";
        return hotelUrl;
    }
}

