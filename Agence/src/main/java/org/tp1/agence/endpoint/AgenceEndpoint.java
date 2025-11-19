package org.tp1.agence.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;
import org.tp1.agence.dto.ReservationResponse;
import org.tp1.agence.service.AgenceService;
import org.tp1.agence.soap.*;

import java.util.List;
import java.util.Map;

/**
 * Endpoint SOAP qui expose les services de l'Agence
 * Le Client SOAP enverra des requêtes SOAP à cet endpoint
 */
@Endpoint
public class AgenceEndpoint {

    private static final String NAMESPACE_URI = "http://tp1.org/agence/soap";

    @Autowired
    private AgenceService agenceService;

    /**
     * Ping - Vérifier que l'agence fonctionne
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "pingRequest")
    @ResponsePayload
    public PingResponse ping(@RequestPayload PingRequest request) {
        PingResponse response = new PingResponse();
        response.setMessage("Agence SOAP opérationnelle");
        return response;
    }

    /**
     * Rechercher des chambres disponibles
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "rechercherChambresRequest")
    @ResponsePayload
    public RechercherChambresResponse rechercherChambres(@RequestPayload RechercherChambresRequest request) {
        // Convertir la requête SOAP en objet DTO
        RechercheRequest rechercheDTO = new RechercheRequest();
        rechercheDTO.setAdresse(request.getAdresse());
        rechercheDTO.setDateArrive(request.getDateArrive());
        rechercheDTO.setDateDepart(request.getDateDepart());

        if (request.getPrixMin() != null) {
            rechercheDTO.setPrixMin(request.getPrixMin());
        }
        if (request.getPrixMax() != null) {
            rechercheDTO.setPrixMax(request.getPrixMax());
        }
        if (request.getNbrEtoile() != null) {
            rechercheDTO.setNbrEtoile(request.getNbrEtoile());
        }
        if (request.getNbrLits() != null) {
            rechercheDTO.setNbrLits(request.getNbrLits());
        }

        // Appeler le service
        List<ChambreDTO> chambres = agenceService.rechercherChambres(rechercheDTO);

        // Convertir la réponse en SOAP
        RechercherChambresResponse response = new RechercherChambresResponse();
        for (ChambreDTO chambreDTO : chambres) {
            Chambre chambreSoap = new Chambre();
            chambreSoap.setId(chambreDTO.getId());
            chambreSoap.setNumero(chambreDTO.getNom());
            chambreSoap.setPrix(chambreDTO.getPrix());
            chambreSoap.setNbrLits(chambreDTO.getNbrDeLit());
            chambreSoap.setHotelNom(chambreDTO.getHotelNom());
            chambreSoap.setHotelAdresse(chambreDTO.getHotelAdresse());
            chambreSoap.setDisponible(true); // Par défaut disponible dans la recherche
            chambreSoap.setImageUrl(chambreDTO.getImageUrl());
            response.getChambres().add(chambreSoap);
        }

        return response;
    }

    /**
     * Effectuer une réservation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "effectuerReservationRequest")
    @ResponsePayload
    public EffectuerReservationResponse effectuerReservation(@RequestPayload EffectuerReservationRequest request) {
        // Convertir la requête SOAP en objet DTO
        ReservationRequest reservationDTO = new ReservationRequest();
        reservationDTO.setClientNom(request.getClient().getNom());
        reservationDTO.setClientPrenom(request.getClient().getPrenom());
        reservationDTO.setClientNumeroCarteBleue(request.getClient().getNumeroCarteBleue());
        reservationDTO.setChambreId(request.getChambreId());
        reservationDTO.setDateArrive(request.getDateArrive());
        reservationDTO.setDateDepart(request.getDateDepart());

        // Appeler le service
        ReservationResponse reservationResponse = agenceService.effectuerReservation(reservationDTO);

        // Convertir la réponse en SOAP
        EffectuerReservationResponse response = new EffectuerReservationResponse();
        response.setReservationId(reservationResponse.getReservationId());
        response.setSuccess(reservationResponse.isSuccess());
        response.setMessage(reservationResponse.getMessage());

        return response;
    }

    /**
     * Récupérer toutes les réservations par hôtel
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllReservationsByHotelRequest")
    @ResponsePayload
    public GetAllReservationsByHotelResponse getAllReservationsByHotel(@RequestPayload GetAllReservationsByHotelRequest request) {
        // Récupérer les réservations de tous les hôtels
        Map<String, List<org.tp1.agence.wsdl.hotel.Reservation>> reservationsParHotel =
            agenceService.getAllReservationsByHotel();

        GetAllReservationsByHotelResponse response = new GetAllReservationsByHotelResponse();

        // Convertir pour chaque hôtel
        for (Map.Entry<String, List<org.tp1.agence.wsdl.hotel.Reservation>> entry : reservationsParHotel.entrySet()) {
            HotelReservations hotelRes = new HotelReservations();
            hotelRes.setHotelNom(entry.getKey());

            for (org.tp1.agence.wsdl.hotel.Reservation hotelReservation : entry.getValue()) {
                Reservation agenceReservation = new Reservation();
                agenceReservation.setId(hotelReservation.getId());
                agenceReservation.setClientNom(hotelReservation.getClient().getNom());
                agenceReservation.setClientPrenom(hotelReservation.getClient().getPrenom());
                agenceReservation.setChambreId(hotelReservation.getChambre().getId());
                agenceReservation.setChambreNom(hotelReservation.getChambre().getNom());
                agenceReservation.setDateArrive(hotelReservation.getDateArrive());
                agenceReservation.setDateDepart(hotelReservation.getDateDepart());

                hotelRes.getReservations().add(agenceReservation);
            }

            response.getHotels().add(hotelRes);
        }

        return response;
    }
}

