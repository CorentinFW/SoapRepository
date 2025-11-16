# 🖥️ CLIENT CLI - Système de Réservation Hôtelière

## 📋 Description

Client en ligne de commande (CLI) Spring Boot pour interagir avec l'agence de réservation d'hôtels.
Le client se connecte à l'agence via REST et permet de :
- Rechercher des chambres disponibles
- Effectuer des réservations
- Consulter les résultats de recherche

## 🏗️ Architecture

```
┌─────────────────┐
│  Client CLI     │
│  (Port: aucun)  │
│                 │
│  Spring Boot    │
│  + RestTemplate │
└────────┬────────┘
         │ REST (HTTP)
         ↓
┌─────────────────┐
│  Agence         │
│  (Port: 8081)   │
│                 │
│  REST API       │
└─────────────────┘
```

## 📦 Structure du Projet

```
Client/
├── pom.xml
├── start-client.sh
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── org/
        │       └── tp1/
        │           └── client/
        │               ├── ClientApplication.java    # Point d'entrée
        │               ├── cli/
        │               │   └── ClientCLI.java        # Interface CLI
        │               ├── service/
        │               │   └── AgenceClientService.java  # Client REST
        │               ├── dto/
        │               │   ├── RechercheRequest.java
        │               │   ├── ChambreDTO.java
        │               │   ├── ReservationRequest.java
        │               │   └── ReservationResponse.java
        │               └── config/
        │                   └── RestTemplateConfig.java
        └── resources/
            └── application.properties
```

## 🚀 Démarrage

### Prérequis

1. **Maven** installé
2. **Java 8+** installé
3. **L'Agence doit être démarrée** sur le port 8081

### Démarrer le Client

```bash
cd /home/corentinfay/Bureau/SoapRepository/Client

# Méthode 1 : Script
./start-client.sh

# Méthode 2 : Maven direct
mvn spring-boot:run
```

## 🎯 Utilisation du CLI

### Menu Principal

Une fois lancé, le client affiche un menu interactif :

```
╔═══════════════════════════════════════════════════╗
║                                                   ║
║     SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT     ║
║                                                   ║
╚═══════════════════════════════════════════════════╝

═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter
```

### 1. Rechercher des Chambres

Permet de rechercher des chambres selon plusieurs critères :

```
Adresse (ville/rue) [optionnel]: Paris
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
Prix minimum [optionnel, 0 pour ignorer]: 0
Prix maximum [optionnel, 0 pour ignorer]: 200
Nombre d'étoiles (1-6) [optionnel, 0 pour ignorer]: 5
Nombre de lits minimum [optionnel, 0 pour ignorer]: 2
```

**Résultat :**
```
✓ 2 chambre(s) trouvée(s):

─────────────────────────────────────────────────────────────────────────
[ID: 2] Chambre Double
  Prix: 120.00€ | Lits: 2 | Hôtel: Grand Hotel Paris
  Adresse: 10 Rue de la Paix, Paris
─────────────────────────────────────────────────────────────────────────
[ID: 4] Chambre Familiale
  Prix: 150.00€ | Lits: 4 | Hôtel: Grand Hotel Paris
  Adresse: 10 Rue de la Paix, Paris
─────────────────────────────────────────────────────────────────────────
```

### 2. Effectuer une Réservation

Permet de réserver une chambre parmi celles trouvées lors de la dernière recherche.

```
ID de la chambre à réserver: 2

Informations du client:
Nom: Dupont
Prénom: Jean
Numéro de carte bleue: 1234567890123456
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
```

**Résultat :**
```
✓ RÉSERVATION CONFIRMÉE !
ID de réservation: 1
Message: Réservation effectuée avec succès
```

### 3. Afficher les Dernières Chambres

Réaffiche les résultats de la dernière recherche effectuée.

### 4. Quitter

Ferme l'application proprement.

## 🔧 Configuration

### application.properties

```properties
server.port=8083
spring.application.name=Client
spring.main.web-application-type=none

# URL de l'agence
agence.url=http://localhost:8081/api/agence
```

**Notes :**
- `web-application-type=none` : Désactive le serveur web Tomcat (pas nécessaire pour un client CLI)
- `agence.url` : URL de l'API REST de l'agence

## 📡 Communication REST

### Endpoints appelés

Le client communique avec l'agence via les endpoints suivants :

1. **Test de connexion**
   ```
   GET http://localhost:8081/api/agence/ping
   ```

2. **Recherche de chambres**
   ```
   POST http://localhost:8081/api/agence/rechercher
   Content-Type: application/json
   
   {
     "adresse": "Paris",
     "dateArrive": "2025-12-01",
     "dateDepart": "2025-12-05",
     "prixMin": 0,
     "prixMax": 200,
     "nbrEtoile": 5,
     "nbrLits": 2
   }
   ```

3. **Réservation**
   ```
   POST http://localhost:8081/api/agence/reserver
   Content-Type: application/json
   
   {
     "clientNom": "Dupont",
     "clientPrenom": "Jean",
     "clientNumeroCarteBleue": "1234567890123456",
     "chambreId": 2,
     "hotelAdresse": "10 Rue de la Paix, Paris",
     "dateArrive": "2025-12-01",
     "dateDepart": "2025-12-05"
   }
   ```

## 🛠️ Dépendances Maven

```xml
<!-- Spring Boot Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Spring Boot Web pour RestTemplate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Jackson pour JSON -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- JLine pour CLI (couleurs, autocomplétion) -->
<dependency>
    <groupId>org.jline</groupId>
    <artifactId>jline</artifactId>
    <version>3.21.0</version>
</dependency>
```

## 🎨 Fonctionnalités du CLI

### Interface Colorée

Le CLI utilise des codes ANSI pour une meilleure lisibilité :
- 🔵 **Bleu** : Recherche
- 🟢 **Vert** : Réservation / Succès
- 🟡 **Jaune** : Information / En cours
- 🔴 **Rouge** : Erreur / Quitter
- 🔷 **Cyan** : Bannière / IDs

### Validation des Entrées

- Vérification de la connexion à l'agence au démarrage
- Gestion des erreurs de saisie (nombres invalides)
- Validation des IDs de chambre avant réservation
- Messages d'erreur explicites

### Workflow Utilisateur

1. L'utilisateur **recherche** des chambres avec ses critères
2. Le client affiche les résultats et les **stocke en mémoire**
3. L'utilisateur peut **consulter** à nouveau les résultats (option 3)
4. Pour **réserver**, le client affiche les chambres disponibles
5. L'utilisateur sélectionne une chambre par son **ID**
6. Le client effectue la réservation et affiche la confirmation

## 🚨 Gestion des Erreurs

### Agence non disponible
```
✗ Échec - L'agence n'est pas disponible
Assurez-vous que l'agence est démarrée sur le port 8081
```

### Aucune chambre trouvée
```
Aucune chambre trouvée pour ces critères
```

### Erreur de réservation
```
✗ Échec de la réservation
Raison: Chambre déjà réservée pour ces dates
```

### ID invalide
```
ID de chambre invalide
```

## 📊 Flow Complet

```
┌──────────┐
│  Client  │
│   CLI    │
└────┬─────┘
     │
     │ 1. GET /ping
     ├──────────────────────────┐
     │                          │
     │               ┌──────────▼───────┐
     │               │     Agence       │
     │               │   (Port 8081)    │
     │               └──────────┬───────┘
     │                          │
     │ 2. POST /rechercher      │
     ├──────────────────────────┤
     │   (RechercheRequest)     │
     │                          │
     │ ◄─────────────────────── │
     │   List<ChambreDTO>       │
     │                          │
     │ 3. Sélection chambre     │
     │    (par l'utilisateur)   │
     │                          │
     │ 4. POST /reserver        │
     ├──────────────────────────┤
     │   (ReservationRequest)   │
     │                          │
     │ ◄─────────────────────── │
     │   ReservationResponse    │
     │                          │
     └──────────┘
```

## 🎓 Exemple de Session Complète

```bash
$ ./start-client.sh

╔═══════════════════════════════════════════════════╗
║                                                   ║
║     SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT     ║
║                                                   ║
╚═══════════════════════════════════════════════════╝

Connexion à l'agence... ✓ Connecté

═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter

Votre choix: 1

═══ RECHERCHE DE CHAMBRES ═══
Adresse (ville/rue) [optionnel]: Paris
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
Prix minimum [optionnel, 0 pour ignorer]: 0
Prix maximum [optionnel, 0 pour ignorer]: 200
Nombre d'étoiles (1-6) [optionnel, 0 pour ignorer]: 5
Nombre de lits minimum [optionnel, 0 pour ignorer]: 2

Recherche en cours...

✓ 2 chambre(s) trouvée(s):

─────────────────────────────────────────────────────────────────────────
[ID: 2] Chambre Double
  Prix: 120.00€ | Lits: 2 | Hôtel: Grand Hotel Paris
  Adresse: 10 Rue de la Paix, Paris
─────────────────────────────────────────────────────────────────────────

═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter

Votre choix: 2

═══ RÉSERVATION ═══

Chambres disponibles:
[ID: 2] Chambre Double - 120.00€ - 2 lits

ID de la chambre à réserver: 2

Informations du client:
Nom: Dupont
Prénom: Jean
Numéro de carte bleue: 1234567890123456
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05

Réservation en cours...

✓ RÉSERVATION CONFIRMÉE !
ID de réservation: 1
Message: Réservation effectuée avec succès

Votre choix: 4

Au revoir !
```

## 🔗 Intégration avec le Système

### Ordre de démarrage

1. **Hotellerie** (Port 8082) - Service SOAP
   ```bash
   cd Hotellerie
   mvn spring-boot:run
   ```

2. **Agence** (Port 8081) - Service REST + Client SOAP
   ```bash
   cd Agence
   mvn spring-boot:run
   ```

3. **Client** (CLI) - Client REST
   ```bash
   cd Client
   mvn spring-boot:run
   ```

## 📝 Notes Techniques

- **Version Spring Boot** : 2.7.18
- **Java** : 8
- **Type d'application** : CLI (pas de serveur web)
- **Client HTTP** : RestTemplate
- **Format JSON** : Jackson
- **Terminal** : Support ANSI colors

## ✅ Avantages du CLI

1. ✅ **Léger** - Pas de serveur web, juste un client
2. ✅ **Interactif** - Interface utilisateur dans le terminal
3. ✅ **Coloré** - Codes ANSI pour meilleure lisibilité
4. ✅ **Validations** - Vérification des entrées utilisateur
5. ✅ **Spring Boot** - Injection de dépendances et configuration
6. ✅ **Pas de REST** - Communication REST uniquement comme client

---

**Auteur** : Système de Réservation Hôtelière  
**Date** : 2025-01-15

