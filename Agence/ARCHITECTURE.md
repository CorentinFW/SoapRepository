# Architecture SOAP - Système de Réservation Hôtelière

## Vue d'ensemble

Ce projet implémente un système de réservation d'hôtel basé sur une architecture distribuée avec :
- **Client** : Application REST Spring Boot
- **Agence** : Service intermédiaire (serveur REST + client SOAP)
- **Hôtels** : Services SOAP Spring Boot

## Architecture Complète

```
┌─────────────┐          ┌─────────────┐          ┌─────────────┐
│   Client    │          │   Agence    │          │   Hôtel 1   │
│   (REST)    │   HTTP   │ (REST/SOAP) │   SOAP   │   (SOAP)    │
│  Port: N/A  │ -------> │  Port: 8081 │ -------> │  Port: 8082 │
└─────────────┘  REST    └─────────────┘  SOAP    └─────────────┘
                                              │
                                              │    ┌─────────────┐
                                              └--> │   Hôtel 2   │
                                            SOAP   │   (SOAP)    │
                                                   │  Port: 8083 │
                                                   └─────────────┘
```

## Composants

### 1. Client (REST)
**À créer**

**Technologie :** Spring Boot (REST uniquement)

**Fonctionnalités :**
- Interface utilisateur (CLI ou Web)
- Envoie des requêtes REST à l'Agence
- Affiche les résultats à l'utilisateur

**Endpoints utilisés :**
- `GET /api/agence/ping` - Test de connexion
- `POST /api/agence/rechercher` - Rechercher des chambres
- `POST /api/agence/reserver` - Effectuer une réservation

### 2. Agence (REST + SOAP)
**✅ Créée**

**Technologie :** Spring Boot (REST serveur + SOAP client)

**Port :** 8081

**Rôle :**
- Serveur REST : Reçoit les requêtes du Client
- Client SOAP : Interroge les Hôtels
- Agrège les résultats de plusieurs hôtels
- Orchestre les réservations

**Endpoints REST exposés :**
- `GET /api/agence/ping`
- `POST /api/agence/rechercher`
- `POST /api/agence/reserver`

**Fichiers principaux :**
```
Agence/
├── controller/AgenceController.java    # Endpoints REST
├── service/AgenceService.java          # Logique métier
├── client/HotelSoapClient.java         # Client SOAP
└── dto/                                # Objets de transfert
```

### 3. Hôtels (SOAP)
**À transformer**

**Technologie :** Spring Boot (SOAP serveur)

**Port :** 8082, 8083, etc.

**Rôle :**
- Exposer des services SOAP
- Gérer les chambres et réservations
- Répondre aux requêtes de l'Agence

**Services SOAP à exposer :**
- `rechercherChambres(critères)` - Recherche de chambres disponibles
- `effectuerReservation(détails)` - Créer une réservation
- `annulerReservation(id)` - Annuler une réservation

## Flux de Communication

### Recherche de Chambre

```
1. Client envoie requête REST à l'Agence
   POST /api/agence/rechercher
   {
     "adresse": "Paris",
     "dateArrive": "2025-12-01",
     "dateDepart": "2025-12-05",
     "prixMin": 0,
     "prixMax": 200,
     "nbrEtoile": 4,
     "nbrLits": 2
   }

2. Agence reçoit la requête REST

3. Agence envoie requête SOAP à chaque Hôtel
   <soap:Envelope>
     <soap:Body>
       <ns:RechercherChambresRequest>
         <adresse>Paris</adresse>
         <dateArrive>2025-12-01</dateArrive>
         ...
       </ns:RechercherChambresRequest>
     </soap:Body>
   </soap:Envelope>

4. Chaque Hôtel répond avec ses chambres disponibles
   <soap:Envelope>
     <soap:Body>
       <ns:RechercherChambresResponse>
         <chambres>
           <chambre>
             <id>1</id>
             <nom>Chambre Double</nom>
             <prix>120.0</prix>
             ...
           </chambre>
         </chambres>
       </ns:RechercherChambresResponse>
     </soap:Body>
   </soap:Envelope>

5. Agence agrège les résultats

6. Agence répond au Client en REST
   [
     {
       "id": 1,
       "nom": "Chambre Double",
       "prix": 120.0,
       "hotelNom": "Hotel Paris",
       ...
     }
   ]

7. Client affiche les résultats
```

### Réservation

```
1. Client envoie requête de réservation
   POST /api/agence/reserver
   {
     "clientNom": "Dupont",
     "chambreId": 1,
     "hotelAdresse": "Paris",
     ...
   }

2. Agence identifie l'hôtel concerné

3. Agence envoie requête SOAP à l'Hôtel
   <soap:EffectuerReservationRequest>
     <client>...</client>
     <chambre>...</chambre>
     <dates>...</dates>
   </soap:EffectuerReservationRequest>

4. Hôtel crée la réservation et répond
   <soap:EffectuerReservationResponse>
     <reservationId>12345</reservationId>
     <success>true</success>
   </soap:EffectuerReservationResponse>

5. Agence répond au Client
   {
     "reservationId": 12345,
     "message": "Réservation effectuée",
     "success": true
   }
```

## Technologies Utilisées

### Agence
- **Spring Boot 2.7.18** (compatible Java 8)
- **spring-boot-starter-web** - REST API
- **spring-boot-starter-web-services** - Client SOAP
- **JAXB** - Marshalling/Unmarshalling XML
- **Maven JAXB2 Plugin** - Génération de classes depuis WSDL

### Hôtel (à faire)
- **Spring Boot 2.7.18**
- **spring-boot-starter-web-services** - Serveur SOAP
- **JAXB** - XML
- **Maven JAXB2 Plugin** - Génération depuis XSD

### Client (à faire)
- **Spring Boot 2.7.18**
- **spring-boot-starter-web** - Client REST
- **RestTemplate** ou **WebClient** - Appels HTTP

## État Actuel du Projet

### ✅ Terminé

1. **Projet Hotellerie** (base)
   - Modèles : Client, Chambre, Hotel, Reservation, Type
   - Gestionnaire : Logique de recherche et réservation
   - ClientCLI : Interface locale

2. **Projet Agence**
   - Structure complète
   - Contrôleur REST fonctionnel
   - Service métier
   - DTOs
   - Client SOAP (version simulation)
   - Configuration Maven
   - ✅ Compilation réussie

### 🚧 À Faire

1. **Transformer Hotellerie en service SOAP**
   - Créer schéma XSD
   - Créer endpoints SOAP
   - Générer WSDL
   - Configurer Spring WS

2. **Compléter le client SOAP de l'Agence**
   - Importer WSDL des hôtels
   - Générer classes Java
   - Implémenter appels SOAP réels

3. **Créer projet Client REST**
   - Application Spring Boot
   - Interface utilisateur
   - Appels REST vers Agence

## Démarrage du Système Complet

Une fois tout implémenté :

```bash
# Terminal 1 - Démarrer Hôtel 1
cd /home/etudiant/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082

# Terminal 2 - Démarrer Agence
cd /home/etudiant/Bureau/SoapRepository/Agence
mvn spring-boot:run

# Terminal 3 - Démarrer Client
cd /home/etudiant/Bureau/SoapRepository/Client
mvn spring-boot:run
```

## Avantages de cette Architecture

1. **Séparation des responsabilités**
   - Client : Interface utilisateur
   - Agence : Orchestration
   - Hôtels : Gestion des données

2. **Scalabilité**
   - Facile d'ajouter de nouveaux hôtels
   - Chaque composant peut être déployé indépendamment

3. **Interopérabilité**
   - SOAP permet la communication inter-entreprises
   - REST offre une API simple pour les clients

4. **Maintenance**
   - Code modulaire
   - Tests isolés par composant

## Prochaine Étape Recommandée

**Transformer le projet Hotellerie en service SOAP**

Cela permettra à l'Agence de communiquer réellement avec les hôtels.

