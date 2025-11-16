# Système de Réservation Hôtelière - Architecture SOAP Complète

## Architecture

```
┌──────────────┐    SOAP     ┌──────────────┐    SOAP     ┌──────────────┐
│    Client    │ ◄─────────► │    Agence    │ ◄─────────► │  Hotellerie  │
│  (Port CLI)  │             │  (Port 8081) │             │  (Port 8082) │
└──────────────┘             └──────────────┘             └──────────────┘
```

**✅ PAS DE REST - 100% SOAP !**

## Composants

### 1. Hotellerie (Port 8082)
- **Type** : Serveur SOAP
- **Rôle** : Gère un hôtel avec ses chambres et réservations
- **WSDL** : http://localhost:8082/ws/hotel.wsdl

### 2. Agence (Port 8081)
- **Type** : Serveur SOAP (pour Client) + Client SOAP (pour Hotellerie)
- **Rôle** : Intermédiaire entre le Client et les Hôtels
- **WSDL** : http://localhost:8081/ws/agence.wsdl

### 3. Client (CLI)
- **Type** : Client SOAP
- **Rôle** : Interface en ligne de commande pour l'utilisateur final
- **Communication** : SOAP avec l'Agence

## Démarrage

### 1. Démarrer Hotellerie
```bash
cd Hotellerie
mvn spring-boot:run
```

### 2. Démarrer Agence
```bash
cd Agence
mvn spring-boot:run
```

### 3. Démarrer Client
```bash
cd Client
mvn spring-boot:run
```

## Utilisation du Client

Le client offre un menu interactif :

1. **Rechercher des chambres** : Rechercher selon critères (prix, étoiles, lits, etc.)
2. **Effectuer une réservation** : Réserver une chambre trouvée
3. **Afficher les dernières chambres** : Revoir les résultats de recherche
4. **Quitter**

## Services SOAP

### Agence (Client ↔ Agence)

#### Ping
```xml
<pingRequest/>
```

#### Rechercher des chambres
```xml
<rechercherChambresRequest>
    <adresse>Paris</adresse>
    <dateArrive>2025-12-01</dateArrive>
    <dateDepart>2025-12-05</dateDepart>
    <prixMin>50</prixMin>
    <prixMax>200</prixMax>
    <nbrEtoile>5</nbrEtoile>
    <nbrLits>2</nbrLits>
</rechercherChambresRequest>
```

#### Effectuer une réservation
```xml
<effectuerReservationRequest>
    <client>
        <nom>Dupont</nom>
        <prenom>Jean</prenom>
        <numeroCarteBleue>1234567890123456</numeroCarteBleue>
    </client>
    <chambreId>2</chambreId>
    <dateArrive>2025-12-10</dateArrive>
    <dateDepart>2025-12-15</dateDepart>
</effectuerReservationRequest>
```

## Tests

### Test SOAP avec curl

#### Ping Agence
```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:pingRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

#### Rechercher des chambres
```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:rechercherChambresRequest>
         <soap:adresse>Paris</soap:adresse>
         <soap:dateArrive>2025-12-01</soap:dateArrive>
         <soap:dateDepart>2025-12-05</soap:dateDepart>
         <soap:prixMax>200</soap:prixMax>
      </soap:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Configuration

### Agence (application.properties)
```properties
server.port=8081
spring.application.name=Agence
hotel.soap.url=http://localhost:8082/ws
```

### Client (application.properties)
```properties
server.port=8083
spring.application.name=Client
spring.main.web-application-type=none
agence.soap.url=http://localhost:8081/ws
```

### Hotellerie (application.properties)
```properties
server.port=8082
spring.application.name=Hotellerie
```

## Technologies

- **Spring Boot 2.7.18**
- **Spring Web Services** (SOAP)
- **JAXB** (Marshalling/Unmarshalling XML)
- **Java 8**
- **Maven**

## Auteurs

Projet de réservation hôtelière avec architecture SOAP complète.

