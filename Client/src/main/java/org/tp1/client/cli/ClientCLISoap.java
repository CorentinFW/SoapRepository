package org.tp1.client.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tp1.client.soap.AgenceSoapClient;
import org.tp1.client.wsdl.agence.*;

import java.util.List;
import java.util.Scanner;

/**
 * Interface CLI pour interagir avec l'agence de réservation via SOAP
 */
@Component
public class ClientCLISoap {

    @Autowired
    private AgenceSoapClient agenceSoapClient;

    private Scanner scanner;
    private List<Chambre> dernieresChambres;

    // Couleurs ANSI
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public void run() {
        scanner = new Scanner(System.in);

        afficherBanniere();

        // Test de connexion à l'agence
        System.out.print("Connexion à l'agence SOAP... ");
        try {
            String message = agenceSoapClient.ping();
            System.out.println(GREEN + "✓ Connecté - " + message + RESET);
        } catch (Exception e) {
            System.out.println(RED + "✗ Échec - L'agence n'est pas disponible" + RESET);
            System.out.println("Assurez-vous que l'agence est démarrée sur le port 8081");
            System.out.println("Erreur: " + e.getMessage());
            return;
        }

        // Boucle principale
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    rechercherChambres();
                    break;
                case 2:
                    effectuerReservation();
                    break;
                case 3:
                    afficherDernieresChambres();
                    break;
                case 4:
                    System.out.println("\n" + CYAN + "Au revoir !" + RESET);
                    continuer = false;
                    break;
                default:
                    System.out.println(RED + "Choix invalide" + RESET);
            }
        }

        scanner.close();
    }

    private void afficherBanniere() {
        System.out.println(CYAN + BOLD);
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║                                                   ║");
        System.out.println("║   SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT SOAP  ║");
        System.out.println("║                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private void afficherMenu() {
        System.out.println("\n" + BOLD + "═══ MENU PRINCIPAL ═══" + RESET);
        System.out.println("1. " + BLUE + "Rechercher des chambres" + RESET);
        System.out.println("2. " + GREEN + "Effectuer une réservation" + RESET);
        System.out.println("3. " + YELLOW + "Afficher les dernières chambres trouvées" + RESET);
        System.out.println("4. " + RED + "Quitter" + RESET);
        System.out.print("\n" + BOLD + "Votre choix: " + RESET);
    }

    private int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void rechercherChambres() {
        System.out.println("\n" + BOLD + BLUE + "═══ RECHERCHE DE CHAMBRES ═══" + RESET);

        // Adresse
        System.out.print("Adresse (ville/rue) [optionnel]: ");
        String adresse = scanner.nextLine().trim();
        if (adresse.isEmpty()) {
            adresse = null;
        }

        // Date d'arrivée
        System.out.print("Date d'arrivée (YYYY-MM-DD): ");
        String dateArrive = scanner.nextLine().trim();

        // Date de départ
        System.out.print("Date de départ (YYYY-MM-DD): ");
        String dateDepart = scanner.nextLine().trim();

        // Prix minimum
        System.out.print("Prix minimum [optionnel, Enter pour ignorer]: ");
        String prixMinStr = scanner.nextLine().trim();
        Float prixMin = prixMinStr.isEmpty() ? null : Float.parseFloat(prixMinStr);

        // Prix maximum
        System.out.print("Prix maximum [optionnel, Enter pour ignorer]: ");
        String prixMaxStr = scanner.nextLine().trim();
        Float prixMax = prixMaxStr.isEmpty() ? null : Float.parseFloat(prixMaxStr);

        // Nombre d'étoiles
        System.out.print("Nombre d'étoiles (1-6) [optionnel, Enter pour ignorer]: ");
        String etoilesStr = scanner.nextLine().trim();
        Integer nbrEtoile = etoilesStr.isEmpty() ? null : Integer.parseInt(etoilesStr);

        // Nombre de lits
        System.out.print("Nombre de lits minimum [optionnel, Enter pour ignorer]: ");
        String litsStr = scanner.nextLine().trim();
        Integer nbrLits = litsStr.isEmpty() ? null : Integer.parseInt(litsStr);

        // Effectuer la recherche
        System.out.println("\n" + YELLOW + "Recherche en cours..." + RESET);
        try {
            RechercherChambresResponse response = agenceSoapClient.rechercherChambres(
                adresse, dateArrive, dateDepart, prixMin, prixMax, nbrEtoile, nbrLits
            );

            dernieresChambres = response.getChambres();

            if (dernieresChambres == null || dernieresChambres.isEmpty()) {
                System.out.println(RED + "Aucune chambre trouvée pour ces critères" + RESET);
            } else {
                System.out.println(GREEN + "\n✓ " + dernieresChambres.size() + " chambre(s) trouvée(s):" + RESET);
                afficherChambres(dernieresChambres);
            }
        } catch (Exception e) {
            System.out.println(RED + "Erreur: " + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }

    private void afficherChambres(List<Chambre> chambres) {
        System.out.println("\n" + BOLD + "─────────────────────────────────────────────────────────────────────────" + RESET);
        for (Chambre chambre : chambres) {
            System.out.printf(CYAN + "[ID: %d]" + RESET + " %s\n", chambre.getId(), chambre.getNumero());
            System.out.printf("  Prix: " + GREEN + "%.2f€" + RESET + " | Lits: %d | Hôtel: %s\n",
                chambre.getPrix(), chambre.getNbrLits(), chambre.getHotelNom());
            System.out.printf("  Adresse: %s\n", chambre.getHotelAdresse());
            System.out.printf("  Disponible: %s\n", chambre.isDisponible() ? GREEN + "Oui" + RESET : RED + "Non" + RESET);
            System.out.println(BOLD + "─────────────────────────────────────────────────────────────────────────" + RESET);
        }
    }

    private void afficherDernieresChambres() {
        if (dernieresChambres == null || dernieresChambres.isEmpty()) {
            System.out.println(YELLOW + "\nAucune recherche effectuée. Faites d'abord une recherche (option 1)" + RESET);
        } else {
            System.out.println(GREEN + "\n✓ Dernières chambres trouvées (" + dernieresChambres.size() + "):" + RESET);
            afficherChambres(dernieresChambres);
        }
    }

    private void effectuerReservation() {
        System.out.println("\n" + BOLD + GREEN + "═══ RÉSERVATION ═══" + RESET);

        // Vérifier qu'une recherche a été effectuée
        if (dernieresChambres == null || dernieresChambres.isEmpty()) {
            System.out.println(YELLOW + "Aucune chambre disponible. Effectuez d'abord une recherche (option 1)" + RESET);
            return;
        }

        // Afficher les chambres disponibles
        System.out.println("\n" + BOLD + "Chambres disponibles:" + RESET);
        afficherChambres(dernieresChambres);

        // ID de la chambre
        System.out.print("\nID de la chambre à réserver: ");
        int chambreId;
        try {
            chambreId = Integer.parseInt(scanner.nextLine().trim());

            // Vérifier que l'ID existe
            Chambre chambreSelectionnee = null;
            for (Chambre c : dernieresChambres) {
                if (c.getId() == chambreId) {
                    chambreSelectionnee = c;
                    break;
                }
            }

            if (chambreSelectionnee == null) {
                System.out.println(RED + "ID de chambre invalide" + RESET);
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "ID invalide" + RESET);
            return;
        }

        // Informations client
        System.out.println("\n" + BOLD + "Informations du client:" + RESET);
        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();

        System.out.print("Prénom: ");
        String prenom = scanner.nextLine().trim();

        System.out.print("Numéro de carte bleue: ");
        String numeroCarteBleue = scanner.nextLine().trim();

        // Dates
        System.out.print("Date d'arrivée (YYYY-MM-DD): ");
        String dateArrive = scanner.nextLine().trim();

        System.out.print("Date de départ (YYYY-MM-DD): ");
        String dateDepart = scanner.nextLine().trim();

        // Effectuer la réservation
        System.out.println("\n" + YELLOW + "Réservation en cours..." + RESET);
        try {
            EffectuerReservationResponse response = agenceSoapClient.effectuerReservation(
                nom, prenom, numeroCarteBleue, chambreId, dateArrive, dateDepart
            );

            if (response.isSuccess()) {
                System.out.println(GREEN + BOLD + "\n✓ RÉSERVATION CONFIRMÉE !" + RESET);
                System.out.println("ID de réservation: " + CYAN + response.getReservationId() + RESET);
                System.out.println("Message: " + response.getMessage());
            } else {
                System.out.println(RED + "\n✗ Échec de la réservation" + RESET);
                System.out.println("Raison: " + response.getMessage());
            }
        } catch (Exception e) {
            System.out.println(RED + "Erreur: " + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }
}

