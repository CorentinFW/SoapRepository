# Agence - Service Intermédiaire SOAP/REST

## Description
L'Agence est un service Spring Boot qui agit comme **intermédiaire** entre :
- **Le Client** (REST) : Reçoit des requêtes HTTP REST du client
- **Les Hôtels** (SOAP) : Envoie des requêtes SOAP aux services des hôtels

## Architecture

```
Client (REST) --HTTP REST--> Agence (port 8081) --SOAP--> Hôtels (SOAP, port 8082)
```

### Rôle de l'Agence
- **Serveur REST** : Expose des endpoints REST pour le client
- **Client SOAP** : Communique avec les hôtels via SOAP

## Ports
- **Agence** : 8081 (REST)
- **Hôtels** : 8082 (SOAP) - configuré dans `application.properties`

## Structure du Projet

```
Agence/
├── src/main/java/org/tp1/agence/
│   ├── AgenceApplication.java          # Point d'entrée Spring Boot
│   ├── controller/
│   │   └── AgenceController.java       # Contrôleur REST
│   ├── service/
│   │   └── AgenceService.java          # Logique métier
│   ├── client/
│   │   └── HotelSoapClient.java        # Client SOAP pour les hôtels
│   └── dto/
│       ├── RechercheRequest.java       # DTO pour recherche
│       ├── ChambreDTO.java             # DTO pour chambre
│       ├── ReservationRequest.java     # DTO pour réservation
│       └── ReservationResponse.java    # DTO pour réponse
└── src/main/resources/
    ├── application.properties          # Configuration
    └── wsdl/                           # Fichiers WSDL des hôtels (à venir)
```

## Compilation et Démarrage

### 1. Compiler le projet
```bash
cd /home/etudiant/Bureau/SoapRepository/Agence
mvn clean install
```

### 2. Démarrer l'application
```bash
mvn spring-boot:run
```

L'application démarrera sur le port **8081**.

## Endpoints REST (pour le Client)

### 1. Ping (test de disponibilité)
```bash
GET http://localhost:8081/api/agence/ping

# Avec curl :
curl http://localhost:8081/api/agence/ping
```

**Réponse attendue :**
```
Agence SOAP opérationnelle
```

### 2. Rechercher des chambres
```bash
POST http://localhost:8081/api/agence/rechercher
Content-Type: application/json

# Exemple de requête :
{
  "adresse": "Paris",
  "dateArrive": "2025-12-01",
  "dateDepart": "2025-12-05",
  "prixMin": 0,
  "prixMax": 200,
  "nbrEtoile": 4,
  "nbrLits": 2
}

# Avec curl :
curl -X POST http://localhost:8081/api/agence/rechercher \
  -H "Content-Type: application/json" \
  -d '{
    "adresse": "Paris",
    "dateArrive": "2025-12-01",
    "dateDepart": "2025-12-05",
    "prixMin": 0,
    "prixMax": 200,
    "nbrEtoile": 4,
    "nbrLits": 2
  }'
```

**Réponse attendue :**
```json
[
  {
    "id": 1,
    "nom": "Chambre Double",
    "prix": 120.0,
    "nbrDeLit": 2,
    "hotelNom": "Hotel Paris",
    "hotelAdresse": "Rue de la Paix, Paris"
  }
]
```

### 3. Effectuer une réservation
```bash
POST http://localhost:8081/api/agence/reserver
Content-Type: application/json

# Exemple de requête :
{
  "clientNom": "Dupont",
  "clientPrenom": "Jean",
  "clientNumeroCarteBleue": "1234567890123456",
  "chambreId": 1,
  "hotelAdresse": "Rue de la Paix, Paris",
  "dateArrive": "2025-12-01",
  "dateDepart": "2025-12-05"
}

# Avec curl :
curl -X POST http://localhost:8081/api/agence/reserver \
  -H "Content-Type: application/json" \
  -d '{
    "clientNom": "Dupont",
    "clientPrenom": "Jean",
    "clientNumeroCarteBleue": "1234567890123456",
    "chambreId": 1,
    "hotelAdresse": "Rue de la Paix, Paris",
    "dateArrive": "2025-12-01",
    "dateDepart": "2025-12-05"
  }'
```

**Réponse attendue :**
```json
{
  "reservationId": 12345,
  "message": "Réservation effectuée avec succès",
  "success": true
}
```

## Configuration SOAP

### Client SOAP
Le client SOAP (`HotelSoapClient.java`) communique avec les hôtels. 

**Version actuelle :**
- Implémentation temporaire/simulation
- Affiche les logs des requêtes

**Version future (une fois les hôtels en SOAP) :**
1. Placer le WSDL des hôtels dans `src/main/resources/wsdl/hotel.wsdl`
2. Maven générera automatiquement les classes Java via `maven-jaxb2-plugin`
3. Implémenter les appels SOAP réels dans `HotelSoapClient`

### Configuration
Fichier : `src/main/resources/application.properties`
```properties
server.port=8081
spring.application.name=Agence
hotel.soap.url=http://localhost:8082/ws
```

## Dépendances Maven

- **spring-boot-starter-web** : Pour les endpoints REST
- **spring-boot-starter-web-services** : Pour le client SOAP
- **wsdl4j** : Pour WSDL
- **jaxb-api, jaxb-impl, jaxb-core** : Pour la manipulation XML (Java 8)
- **maven-jaxb2-plugin** : Pour générer les classes à partir de WSDL

## Prochaines Étapes

### Étape 1 : Transformer l'Hôtel en service SOAP
1. Ajouter les dépendances SOAP dans le projet Hotellerie
2. Créer le schéma XSD pour les messages SOAP
3. Créer l'endpoint SOAP dans Hotellerie
4. Générer le WSDL

### Étape 2 : Connecter l'Agence aux Hôtels
1. Récupérer le WSDL des hôtels
2. Le placer dans `Agence/src/main/resources/wsdl/`
3. Recompiler l'Agence (génération auto des classes)
4. Implémenter les appels SOAP dans `HotelSoapClient`

### Étape 3 : Créer le Client REST
1. Créer un nouveau projet Spring Boot "Client"
2. Utiliser RestTemplate ou WebClient pour appeler l'Agence
3. Créer une interface utilisateur (CLI ou Web)

## Tests Rapides

Une fois l'application démarrée :

```bash
# Test 1 : Vérifier que l'agence répond
curl http://localhost:8081/api/agence/ping

# Test 2 : Recherche de chambres
curl -X POST http://localhost:8081/api/agence/rechercher \
  -H "Content-Type: application/json" \
  -d '{"adresse":"Paris","dateArrive":"2025-12-01","dateDepart":"2025-12-05","prixMin":0,"prixMax":200,"nbrEtoile":4,"nbrLits":2}'
```

## Logs

Les logs affichent :
- Les requêtes reçues du client
- Les appels SOAP effectués vers les hôtels
- Les erreurs éventuelles

## Troubleshooting

**Erreur : Port 8081 déjà utilisé**
```bash
# Trouver le processus
lsof -i :8081
# Ou changer le port dans application.properties
```

**Erreur : Cannot find classes from WSDL**
```bash
# S'assurer que le WSDL est dans src/main/resources/wsdl/
# Recompiler
mvn clean compile
```

