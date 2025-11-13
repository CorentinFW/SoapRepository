package org.tp1.hotellerie.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.tp1.hotellerie.model.Chambre;
import org.tp1.hotellerie.model.Hotel;
import org.tp1.hotellerie.model.Reservation;
import org.tp1.hotellerie.soap.generated.*;

import java.util.List;

/**
 * Endpoint SOAP pour les services de l'hôtel
 */
@Endpoint
public class HotelEndpoint {

    private static final String NAMESPACE_URI = "http://tp1.org/hotellerie/soap";

    @Autowired
    private HotelService hotelService;

    /**
     * Rechercher des chambres disponibles
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "rechercherChambresRequest")
    @ResponsePayload
    public RechercherChambresResponse rechercherChambres(@RequestPayload RechercherChambresRequest request) {
        List<Chambre> chambres = hotelService.rechercherChambres(
            request.getAdresse(),
            request.getDateArrive(),
            request.getDateDepart(),
            request.getPrixMin(),
            request.getPrixMax(),
            request.getNbrEtoile(),
            request.getNbrLits()
        );

        RechercherChambresResponse response = new RechercherChambresResponse();
        Hotel hotel = hotelService.getHotel();
        response.setHotelNom(hotel.getNom());
        response.setHotelAdresse(hotel.getAdresse());

        for (Chambre chambre : chambres) {
            org.tp1.hotellerie.soap.generated.Chambre soapChambre = new org.tp1.hotellerie.soap.generated.Chambre();
            soapChambre.setId(chambre.getId());
            soapChambre.setNom(chambre.getNom());
            soapChambre.setPrix(chambre.getPrix());
            soapChambre.setNbrDeLit(chambre.getNbrDeLit());
            response.getChambres().add(soapChambre);
        }

        return response;
    }

    /**
     * Effectuer une réservation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "effectuerReservationRequest")
    @ResponsePayload
    public EffectuerReservationResponse effectuerReservation(@RequestPayload EffectuerReservationRequest request) {
        // Convertir le client SOAP en client modèle
        org.tp1.hotellerie.model.Client client = new org.tp1.hotellerie.model.Client(
            request.getClient().getNom(),
            request.getClient().getPrenom(),
            request.getClient().getNumeroCarteBleue()
        );

        HotelService.ReservationResult result = hotelService.effectuerReservation(
            client,
            request.getChambreId(),
            request.getDateArrive(),
            request.getDateDepart()
        );

        EffectuerReservationResponse response = new EffectuerReservationResponse();
        response.setReservationId(result.getReservationId());
        response.setSuccess(result.isSuccess());
        response.setMessage(result.getMessage());

        return response;
    }

    /**
     * Obtenir les informations de l'hôtel
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHotelInfoRequest")
    @ResponsePayload
    public GetHotelInfoResponse getHotelInfo(@RequestPayload GetHotelInfoRequest request) {
        Hotel hotel = hotelService.getHotel();

        org.tp1.hotellerie.soap.generated.Hotel soapHotel = new org.tp1.hotellerie.soap.generated.Hotel();
        soapHotel.setNom(hotel.getNom());
        soapHotel.setAdresse(hotel.getAdresse());
        soapHotel.setType(TypeHotel.valueOf(hotel.getType().name()));
        soapHotel.setNombreChambres(hotel.getNombreChambres());
        soapHotel.setNombreReservations(hotel.getNombreReservations());

        GetHotelInfoResponse response = new GetHotelInfoResponse();
        response.setHotel(soapHotel);

        return response;
    }

    /**
     * Obtenir toutes les réservations
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationsRequest")
    @ResponsePayload
    public GetReservationsResponse getReservations(@RequestPayload GetReservationsRequest request) {
        List<Reservation> reservations = hotelService.getReservations();

        GetReservationsResponse response = new GetReservationsResponse();

        for (Reservation res : reservations) {
            org.tp1.hotellerie.soap.generated.Reservation soapRes = new org.tp1.hotellerie.soap.generated.Reservation();
            soapRes.setId(res.getId());

            // Client
            org.tp1.hotellerie.soap.generated.Client soapClient = new org.tp1.hotellerie.soap.generated.Client();
            soapClient.setNom(res.getClient().getNom());
            soapClient.setPrenom(res.getClient().getPrenom());
            soapClient.setNumeroCarteBleue(res.getClient().getNumeroCarteBleue());
            soapRes.setClient(soapClient);

            // Chambre
            org.tp1.hotellerie.soap.generated.Chambre soapChambre = new org.tp1.hotellerie.soap.generated.Chambre();
            soapChambre.setId(res.getChambre().getId());
            soapChambre.setNom(res.getChambre().getNom());
            soapChambre.setPrix(res.getChambre().getPrix());
            soapChambre.setNbrDeLit(res.getChambre().getNbrDeLit());
            soapRes.setChambre(soapChambre);

            // Dates
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            soapRes.setDateArrive(sdf.format(res.getDateArrive()));
            soapRes.setDateDepart(sdf.format(res.getDateDepart()));

            response.getReservations().add(soapRes);
        }

        return response;
    }
}

