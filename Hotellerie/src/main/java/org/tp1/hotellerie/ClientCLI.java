package org.tp1.hotellerie;

import org.tp1.hotellerie.model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class ClientCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {
        System.out.println("=== CLI Hotellerie (mode local) ===");

        Gestionnaire gestionnaire = new Gestionnaire();
        Hotel hotel = creerHotelDeTest();
        gestionnaire.getListeDHotel().add(hotel); // on ajoute l'hotel au gestionnaire

        Client client = creerClientInteractive();

        while (true) {
            System.out.println("\nQue voulez-vous faire ?");
            System.out.println("1) Rechercher une chambre");
            System.out.println("2) Afficher les hôtels");
            System.out.println("3) Quitter");
            System.out.print("> ");
            String choix = scanner.nextLine().trim();
            if ("3".equals(choix)) {
                System.out.println("Au revoir");
                break;
            } else if ("1".equals(choix)) {
                faireRechercheEtReservation(gestionnaire, hotel, client);
            } else if ("2".equals(choix)) {
                afficherHotels(gestionnaire);
            } else {
                System.out.println("Choix invalide");
            }
        }
    }

    private static Client creerClientInteractive() {
        System.out.println("Création d'un client :");
        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine().trim();
        System.out.print("Numéro de carte bleue: ");
        String cb = scanner.nextLine().trim();
        Client c = new Client(nom, prenom, cb);
        System.out.println("Client créé: " + c.getNom() + " " + c.getPrenom());
        return c;
    }

    private static void faireRechercheEtReservation(Gestionnaire gestionnaire, Hotel hotel, Client client) {
        System.out.println("--- Recherche ---");
        System.out.print("Adresse (laisser vide pour n'importe laquelle): ");
        String adresse = scanner.nextLine();
        System.out.print("Date d'arrivée (yyyy-MM-dd): ");
        String dateArrive = scanner.nextLine();
        System.out.print("Date de départ (yyyy-MM-dd): ");
        String dateDepart = scanner.nextLine();
        System.out.print("Prix min (0 pour ignoré): ");
        float prixMin = parseFloatOrZero(scanner.nextLine());
        System.out.print("Prix max (0 pour ignoré): ");
        float prixMax = parseFloatOrZero(scanner.nextLine());
        System.out.print("Nombre d'étoiles (0 pour ignorer): ");
        int nbEtoile = parseIntOrZero(scanner.nextLine());
        System.out.print("Nombre de lits requis (0 pour ignorer): ");
        int nbLits = parseIntOrZero(scanner.nextLine());

        Chambre trouve = gestionnaire.recherche(adresse, dateArrive, dateDepart, prixMax, prixMin, nbEtoile, nbLits);
        if (trouve == null) {
            System.out.println("Aucune chambre trouvée correspondant aux critères.");
            return;
        }
        System.out.println("Chambre trouvée: id=" + trouve.getId() + ", nom=" + trouve.getNom() + ", prix=" + trouve.getPrix());
        System.out.print("Voulez-vous réserver ? (o/n): ");
        String r = scanner.nextLine().trim().toLowerCase();
        if ("o".equals(r) || "oui".equals(r)) {
            try {
                Date da = SDF.parse(dateArrive);
                Date dd = SDF.parse(dateDepart);
                Reservation reservation = new Reservation(new Random().nextInt(100000), client, trouve, da, dd);
                gestionnaire.rajouteReservation(hotel, reservation);
                System.out.println("Réservation créée (id=" + reservation.getId() + ") pour la chambre " + trouve.getNom());
            } catch (Exception e) {
                System.out.println("Erreur lors de la création de la réservation: " + e.getMessage());
            }
        }
    }

    private static void afficherHotels(Gestionnaire gestionnaire) {
        System.out.println("--- Liste des hôtels ---");
        List<Hotel> hotels = gestionnaire.getListeDHotel();
        if (hotels.isEmpty()) {
            System.out.println("Aucun hôtel disponible.");
            return;
        }
        for (int i = 0; i < hotels.size(); i++) {
            Hotel h = hotels.get(i);
            System.out.println((i+1) + ") " + h.getNom() + " - " + h.getAdresse() + " - " + (h.getType() != null ? h.getType() : "N/A") + " - chambres: " + h.getNombreChambres() + " - réservations: " + h.getNombreReservations());
            for (Chambre c : h.getListeDesChambres()) {
                System.out.println("   - id=" + c.getId() + ", " + c.getNom() + ", prix=" + c.getPrix() + ", lits=" + c.getNbrDeLit());
            }
        }
    }

    private static float parseFloatOrZero(String s) {
        try {
            return Float.parseFloat(s.trim());
        } catch (Exception e) {
            return 0f;
        }
    }

    private static int parseIntOrZero(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static Hotel creerHotelDeTest() {
        Hotel h = new Hotel("Hotel de Test", "Rue de la Paix, Paris", Type.CAT4);

        h.ajoutChambre(new Chambre(1, "Chambre Simple", 50.0f, 1));
        h.ajoutChambre(new Chambre(2, "Chambre Double", 80.0f, 2));
        h.ajoutChambre(new Chambre(3, "Suite Familiale", 150.0f, 4));

        return h;
    }
}
