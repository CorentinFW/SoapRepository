package org.tp1.hotellerie.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tp1.hotellerie.model.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service SOAP pour gérer l'hôtel
 */
@Service
public class HotelService {

    private Hotel hotel;
    private AtomicInteger reservationIdCounter = new AtomicInteger(1);

    @Value("${hotel.nom:Grand Hotel Paris}")
    private String hotelNom;

    @Value("${hotel.adresse:10 Rue de la Paix, Paris}")
    private String hotelAdresse;

    @Value("${hotel.categorie:CAT5}")
    private String hotelCategorie;

    @Value("${hotel.ville:Paris}")
    private String hotelVille;

    @Value("${server.port:8082}")
    private int serverPort;

    @PostConstruct
    public void init() {
        // Convertir la catégorie String en Type enum
        Type type = Type.valueOf(hotelCategorie);

        // Initialiser l'hôtel avec des données de configuration
        hotel = new Hotel(hotelNom, hotelAdresse, type);

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Initialisation Hôtel: " + hotelVille);
        System.out.println("  Nom: " + hotelNom);
        System.out.println("  Adresse: " + hotelAdresse);
        System.out.println("  Catégorie: " + type);
        System.out.println("═══════════════════════════════════════════");

        // Déterminer l'image selon la ville
        String imageFileName = getImageFileName();
        String imageUrl = "http://localhost:" + serverPort + "/images/" + imageFileName;

        // Ajouter des chambres différentes selon la ville
        if ("Paris".equals(hotelVille)) {
            hotel.ajoutChambre(new Chambre(1, "Chambre Simple", 80.0f, 1, imageUrl));
            hotel.ajoutChambre(new Chambre(2, "Chambre Double", 120.0f, 2, imageUrl));
            hotel.ajoutChambre(new Chambre(3, "Suite Deluxe", 200.0f, 3, imageUrl));
            hotel.ajoutChambre(new Chambre(4, "Chambre Familiale", 150.0f, 4, imageUrl));
            hotel.ajoutChambre(new Chambre(5, "Chambre Economy", 60.0f, 1, imageUrl));
        } else if ("Lyon".equals(hotelVille)) {
            hotel.ajoutChambre(new Chambre(11, "Chambre Standard", 70.0f, 1, imageUrl));
            hotel.ajoutChambre(new Chambre(12, "Chambre Confort", 100.0f, 2, imageUrl));
            hotel.ajoutChambre(new Chambre(13, "Suite Junior", 150.0f, 2, imageUrl));
            hotel.ajoutChambre(new Chambre(14, "Chambre Triple", 130.0f, 3, imageUrl));
            hotel.ajoutChambre(new Chambre(15, "Chambre Budget", 50.0f, 1, imageUrl));
        } else if ("Montpellier".equals(hotelVille)) {
            hotel.ajoutChambre(new Chambre(21, "Chambre Eco", 45.0f, 1, imageUrl));
            hotel.ajoutChambre(new Chambre(22, "Chambre Double Confort", 85.0f, 2, imageUrl));
            hotel.ajoutChambre(new Chambre(23, "Suite Vue Mer", 140.0f, 2, imageUrl));
            hotel.ajoutChambre(new Chambre(24, "Chambre Quad", 110.0f, 4, imageUrl));
            hotel.ajoutChambre(new Chambre(25, "Studio", 65.0f, 1, imageUrl));
        }

        System.out.println("  Chambres ajoutées: " + hotel.getListeDesChambres().size());
        System.out.println("  URL image: " + imageUrl);
        System.out.println("═══════════════════════════════════════════");
    }

    private String getImageFileName() {
        switch (hotelVille) {
            case "Paris":
                return "Hotelle1.png";
            case "Lyon":
                return "Hotelle2.png";
            case "Montpellier":
                return "Hotelle3.png";
            default:
                return "Hotelle1.png";
        }
    }

    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Recherche des chambres disponibles selon des critères
     */
    public List<Chambre> rechercherChambres(String adresse, String dateArrive, String dateDepart,
                                            Float prixMin, Float prixMax, Integer nbrEtoile, Integer nbrLits) {

        List<Chambre> chambresDisponibles = new ArrayList<>();

        // Parsing des dates
        Date arrive = parseDate(dateArrive);
        Date depart = parseDate(dateDepart);

        if (arrive == null || depart == null || !arrive.before(depart)) {
            return chambresDisponibles;
        }

        // Vérifier si l'adresse correspond
        if (adresse != null && !adresse.trim().isEmpty()) {
            if (hotel.getAdresse() == null || !hotel.getAdresse().toLowerCase().contains(adresse.toLowerCase())) {
                return chambresDisponibles;
            }
        }

        // Vérifier le nombre d'étoiles
        if (nbrEtoile != null && nbrEtoile >= 1 && nbrEtoile <= 6) {
            if (hotel.getType() == null || hotel.getType().ordinal() + 1 != nbrEtoile) {
                return chambresDisponibles;
            }
        }

        // Parcourir les chambres
        for (Chambre chambre : hotel.getListeDesChambres()) {
            if (chambre == null) continue;

            // Vérifier le prix
            if (prixMin != null && prixMin > 0 && chambre.getPrix() < prixMin) continue;
            if (prixMax != null && prixMax > 0 && chambre.getPrix() > prixMax) continue;

            // Vérifier le nombre de lits
            if (nbrLits != null && nbrLits > 0 && chambre.getNbrDeLit() < nbrLits) continue;

            // Vérifier la disponibilité
            boolean disponible = true;
            for (Reservation r : hotel.getListeReservation()) {
                if (r == null || r.getChambre() == null) continue;
                if (r.getChambre().getId() != chambre.getId()) continue;

                Date resArrive = parseDate(formatDate(r.getDateArrive()));
                Date resDepart = parseDate(formatDate(r.getDateDepart()));

                if (resArrive != null && resDepart != null) {
                    if (datesChevauchent(arrive, depart, resArrive, resDepart)) {
                        disponible = false;
                        break;
                    }
                }
            }

            if (disponible) {
                chambresDisponibles.add(chambre);
            }
        }

        return chambresDisponibles;
    }

    /**
     * Effectue une réservation
     */
    public ReservationResult effectuerReservation(Client client, int chambreId, String dateArrive, String dateDepart) {
        // Vérifier que le client est valide
        if (client == null || client.getNom() == null || client.getNom().isEmpty()) {
            return new ReservationResult(0, false, "Client invalide");
        }

        // Trouver la chambre
        Chambre chambre = null;
        for (Chambre c : hotel.getListeDesChambres()) {
            if (c.getId() == chambreId) {
                chambre = c;
                break;
            }
        }

        if (chambre == null) {
            return new ReservationResult(0, false, "Chambre non trouvée");
        }

        // Vérifier les dates
        Date arrive = parseDate(dateArrive);
        Date depart = parseDate(dateDepart);

        if (arrive == null || depart == null || !arrive.before(depart)) {
            return new ReservationResult(0, false, "Dates invalides");
        }

        // Vérifier la disponibilité
        for (Reservation r : hotel.getListeReservation()) {
            if (r.getChambre().getId() == chambreId) {
                Date resArrive = parseDate(formatDate(r.getDateArrive()));
                Date resDepart = parseDate(formatDate(r.getDateDepart()));

                if (resArrive != null && resDepart != null) {
                    if (datesChevauchent(arrive, depart, resArrive, resDepart)) {
                        return new ReservationResult(0, false, "Chambre déjà réservée pour ces dates");
                    }
                }
            }
        }

        // Créer la réservation
        int reservationId = reservationIdCounter.getAndIncrement();
        Reservation reservation = new Reservation(reservationId, client, chambre, arrive, depart);
        hotel.ajoutReservation(reservation);

        return new ReservationResult(reservationId, true, "Réservation effectuée avec succès");
    }

    /**
     * Obtenir toutes les réservations
     */
    public List<Reservation> getReservations() {
        return hotel.getListeReservation();
    }

    // Méthodes utilitaires
    private Date parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    private String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private boolean datesChevauchent(Date d1Start, Date d1End, Date d2Start, Date d2End) {
        return d1Start.before(d2End) && d2Start.before(d1End);
    }

    /**
     * Classe pour retourner le résultat d'une réservation
     */
    public static class ReservationResult {
        private final int reservationId;
        private final boolean success;
        private final String message;

        public ReservationResult(int reservationId, boolean success, String message) {
            this.reservationId = reservationId;
            this.success = success;
            this.message = message;
        }

        public int getReservationId() {
            return reservationId;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}

