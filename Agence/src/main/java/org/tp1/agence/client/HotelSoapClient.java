package org.tp1.agence.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Client SOAP pour communiquer avec les hôtels.
 * Cette version est temporaire et sera remplacée par un vrai client SOAP
 * une fois que les services SOAP des hôtels seront implémentés.
 */
@Component
public class HotelSoapClient {

    @Value("${hotel.soap.url}")
    private String hotelSoapUrl;

    /**
     * Recherche des chambres dans tous les hôtels via SOAP
     */
    public List<ChambreDTO> rechercherChambres(RechercheRequest request) {
        // TODO: Implémenter l'appel SOAP réel aux hôtels
        // Pour l'instant, retourne une liste vide
        // Cela sera remplacé par des appels SOAP réels aux services des hôtels

        List<ChambreDTO> chambres = new ArrayList<>();

        // Simulation temporaire - à remplacer par l'appel SOAP réel
        System.out.println("Recherche de chambres via SOAP aux hôtels...");
        System.out.println("URL: " + hotelSoapUrl);
        System.out.println("Critères: adresse=" + request.getAdresse() +
                         ", dates=" + request.getDateArrive() + " -> " + request.getDateDepart() +
                         ", prix=" + request.getPrixMin() + "-" + request.getPrixMax() +
                         ", étoiles=" + request.getNbrEtoile() +
                         ", lits=" + request.getNbrLits());

        return chambres;
    }

    /**
     * Effectue une réservation dans un hôtel via SOAP
     */
    public int effectuerReservation(ReservationRequest request) {
        // TODO: Implémenter l'appel SOAP réel pour la réservation
        // Pour l'instant, retourne un ID fictif

        System.out.println("Réservation via SOAP pour la chambre " + request.getChambreId() +
                         " à l'hôtel " + request.getHotelAdresse());

        // Retourne un ID de réservation fictif
        return (int) (Math.random() * 100000);
    }
}

