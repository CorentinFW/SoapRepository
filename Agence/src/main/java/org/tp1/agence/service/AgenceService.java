package org.tp1.agence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tp1.agence.client.MultiHotelSoapClient;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;
import org.tp1.agence.dto.ReservationResponse;

import java.util.List;

/**
 * Service de l'agence qui orchestre les appels SOAP aux hôtels
 */
@Service
public class AgenceService {

    @Autowired
    private MultiHotelSoapClient hotelSoapClient;

    /**
     * Recherche des chambres disponibles dans tous les hôtels
     */
    public List<ChambreDTO> rechercherChambres(RechercheRequest request) {
        // Validation des paramètres
        if (request.getDateArrive() == null || request.getDateArrive().isEmpty()) {
            throw new IllegalArgumentException("La date d'arrivée est obligatoire");
        }
        if (request.getDateDepart() == null || request.getDateDepart().isEmpty()) {
            throw new IllegalArgumentException("La date de départ est obligatoire");
        }

        // Appel au client SOAP pour interroger les hôtels
        return hotelSoapClient.rechercherChambres(request);
    }

    /**
     * Effectue une réservation dans un hôtel
     */
    public ReservationResponse effectuerReservation(ReservationRequest request) {
        // Validation des paramètres
        if (request.getClientNom() == null || request.getClientNom().isEmpty()) {
            return new ReservationResponse(0, "Le nom du client est obligatoire", false);
        }
        if (request.getClientPrenom() == null || request.getClientPrenom().isEmpty()) {
            return new ReservationResponse(0, "Le prénom du client est obligatoire", false);
        }
        if (request.getChambreId() <= 0) {
            return new ReservationResponse(0, "L'ID de la chambre est invalide", false);
        }

        try {
            // Appel au client SOAP pour effectuer la réservation
            int reservationId = hotelSoapClient.effectuerReservation(request);
            return new ReservationResponse(
                reservationId,
                "Réservation effectuée avec succès",
                true
            );
        } catch (Exception e) {
            return new ReservationResponse(
                0,
                "Erreur lors de la réservation: " + e.getMessage(),
                false
            );
        }
    }
}

