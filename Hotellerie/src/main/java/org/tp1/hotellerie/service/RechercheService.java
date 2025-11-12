package org.tp1.hotellerie.service;

import org.tp1.hotellerie.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class RechercheService {
    private final List<Hotel> hotels = new ArrayList<>();
    public List<Hotel> getHotels() {
        return hotels;
    }

    public void addHotel(Hotel hotel) {
        if (hotel != null && !hotels.contains(hotel)) {
            hotels.add(hotel);
        }
    }

    // UML signature (typo conservée sur "adress" et type ArrayList)
    public ArrayList<Chambre> rechercheHotel(String adress, String dateArrive,
                                             String dateDepart, float prixMax, float prixMin,
                                             int nbrEtoile, int client) {
        // Délègue à une implémentation plus souple si besoin d'évolution ultérieure
        return recherche(adress, dateArrive, dateDepart, prixMin, prixMax, nbrEtoile, client);
    }

    // Implémentation interne: gère bornes nulles et swap min/max
    private ArrayList<Chambre> recherche(String adress, String dateArrive,
                                         String dateDepart, Float prixMin, Float prixMax,
                                         Integer nbrEtoile, Integer nbPersonnes) {
        LocalDate arrive = parseDate(dateArrive);
        LocalDate depart = parseDate(dateDepart);
        if (depart.isBefore(arrive) || depart.equals(arrive)) {
            throw new IllegalArgumentException("dateDepart doit être après dateArrive (format ISO yyyy-MM-dd)");
        }

        // Normalisation bornes prix
        Float min = prixMin;
        Float max = prixMax;
        if (min != null && max != null && min > max) {
            float tmp = min;
            min = max;
            max = tmp;
        }
        final Float minF = min;
        final Float maxF = max;

        final String searchAddress = adress == null ? null : adress.toLowerCase(Locale.ROOT).trim();
        final Integer etoiles = nbrEtoile;
        final Integer personnes = nbPersonnes;

        List<Chambre> result = hotels.stream()
                .filter(h -> searchAddress == null ||
                        (h.getAdresse() != null && h.getAdresse().toLowerCase(Locale.ROOT).contains(searchAddress)))
                .filter(h -> etoiles == null || etoiles <= 0 || h.getNbrEtoile() >= etoiles)
                .flatMap(h -> h.getChambres().stream())
                .filter(ch -> minF == null || ch.getPrix() >= minF)
                .filter(ch -> maxF == null || ch.getPrix() <= maxF)
                .filter(ch -> personnes == null || personnes <= 0 || ch.getNbrDeLit() >= personnes)
                .filter(ch -> isDisponible(ch, arrive, depart))
                .collect(Collectors.toList());

        return new ArrayList<>(result);
    }

    private static LocalDate parseDate(String iso) {
        try {
            return LocalDate.parse(Objects.requireNonNull(iso, "date requise"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date invalide, utilisez le format ISO yyyy-MM-dd: " + iso, e);
        }
    }

    private static boolean isDisponible(Chambre chambre, LocalDate debut, LocalDate fin) {
        for (Reservation r : chambre.getReservations()) {
            LocalDate rDebut = parseDate(r.getDateArrive());
            LocalDate rFin = parseDate(r.getDateDepart());
            // Chevauchement si [debut, fin) intersecte [rDebut, rFin)
            boolean chevauche = debut.isBefore(rFin) && fin.isAfter(rDebut);
            if (chevauche) return false;
        }
        return true;
    }
}