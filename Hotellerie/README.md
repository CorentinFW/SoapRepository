# Hotellerie - Modèle UML implémenté

Ce projet Spring Boot (Java 17) implémente le modèle du système d’hôtel décrit dans `Soap_UML_v1.*`.

Composants principaux:
- Domaine (`org.tp1.hotellerie.model`):
  - `Hotel` (nom, adresse, nbrEtoile) avec associations vers `Chambre` (0..n) et `Reservation` (0..n) et l’opération `reservation(Client, Chambre, dateArrive, dateDepart)`.
  - `Chambre` (id, nom, prix, nbrDeLit) liée à un `Hotel` (1) et `Reservation` (0..n).
  - `Client` (nom, prenom, carteDeCredit) avec `Reservation` (0..n).
  - `Reservation` (dateArrive, dateDepart) liant `Client` (1) et `Chambre` (1).
- Service (`org.tp1.hotellerie.service`):
  - `RechercheService` expose `ArrayList<Chambre> rechercheHotel(String adress, String dateArrive, String dateDepart, float prixMax, float prixMin, int nbrEtoile, int client)` conformément à l’UML. 
    - Filtre par adresse (contient, insensible à la casse), étoiles min, bornes de prix (min/max), capacité (nb de personnes) et disponibilité sur la période [dateArrive, dateDepart).

Format des dates: ISO `yyyy-MM-dd` (ex: `2025-11-18`).

## Lancer les tests

```bash
./mvnw -DskipTests=false test
```

Exécuter uniquement les tests de recherche:

```bash
./mvnw -Dtest=RechercheServiceTests test
```

## Exemple d’utilisation rapide

```java
RechercheService service = new RechercheService();
Hotel h = new Hotel("Hotel A", "Paris", 4);
Chambre ch = new Chambre(1, "101", 120f, 2);
h.addChambre(ch);
service.addHotel(h);

// Réserver
Client c = new Client("Doe", "John", 1234);
h.reservation(c, ch, "2025-11-15", "2025-11-18");

// Rechercher
var chambres = service.rechercheHotel("Paris", "2025-11-18", "2025-11-20", 150f, 50f, 4, 2);
```

## Hypothèses et décisions (appliquées)
1. Types des prix: `prixMin` et `prixMax` sont typés `float` comme dans l’UML. Les bornes sont normalisées (si min > max, elles sont échangées) avant filtrage.
2. Paramètre `client`: interprété comme nombre de personnes. La capacité est vérifiée via `nbrDeLit` de la chambre (`>= client`).
3. Dates: format ISO `yyyy-MM-dd`. `dateDepart` doit être strictement après `dateArrive`. La disponibilité est évaluée sur l’intervalle demi-ouvert `[dateArrive, dateDepart)` avec détection de chevauchement de périodes.

## Notes
- La méthode `rechercheHotel` conserve la signature (noms/types) du diagramme UML.
- La logique de disponibilité considère qu’il y a conflit s’il y a chevauchement de périodes.
- Aucun accès base de données n’est implémenté (stockage en mémoire). Intégration SOAP/REST hors périmètre de cette étape.
