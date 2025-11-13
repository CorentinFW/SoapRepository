# Service SOAP Hotellerie

## Description

Le projet Hotellerie a été transformé en **service SOAP** Spring Boot. Il expose des services web SOAP pour la gestion d'un hôtel (chambres, réservations).

## Architecture

```
Agence (Client SOAP) --SOAP--> Hotellerie (Serveur SOAP)
       Port 8081                     Port 8082
```

## Configuration

- **Port** : 8082
- **Endpoint SOAP** : http://localhost:8082/ws
- **WSDL** : http://localhost:8082/ws/hotel.wsdl

## Services SOAP Exposés

### 1. getHotelInfo
Obtenir les informations de l'hôtel

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:getHotelInfoRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Response:**
```xml
<hot:getHotelInfoResponse>
    <hot:hotel>
        <hot:nom>Grand Hotel Paris</hot:nom>
        <hot:adresse>10 Rue de la Paix, Paris</hot:adresse>
        <hot:type>CAT5</hot:type>
        <hot:nombreChambres>5</hot:nombreChambres>
        <hot:nombreReservations>0</hot:nombreReservations>
    </hot:hotel>
</hot:getHotelInfoResponse>
```

### 2. rechercherChambres
Rechercher des chambres disponibles selon des critères

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:rechercherChambresRequest>
         <hot:adresse>Paris</hot:adresse>
         <hot:dateArrive>2025-12-01</hot:dateArrive>
         <hot:dateDepart>2025-12-05</hot:dateDepart>
         <hot:prixMin>0</hot:prixMin>
         <hot:prixMax>200</hot:prixMax>
         <hot:nbrEtoile>5</hot:nbrEtoile>
         <hot:nbrLits>2</hot:nbrLits>
      </hot:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Response:**
```xml
<hot:rechercherChambresResponse>
    <hot:chambres>
        <hot:id>2</hot:id>
        <hot:nom>Chambre Double</hot:nom>
        <hot:prix>120.0</hot:prix>
        <hot:nbrDeLit>2</hot:nbrDeLit>
    </hot:chambres>
    <hot:hotelNom>Grand Hotel Paris</hot:hotelNom>
    <hot:hotelAdresse>10 Rue de la Paix, Paris</hot:hotelAdresse>
</hot:rechercherChambresResponse>
```

### 3. effectuerReservation
Créer une réservation

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:effectuerReservationRequest>
         <hot:client>
            <hot:nom>Dupont</hot:nom>
            <hot:prenom>Jean</hot:prenom>
            <hot:numeroCarteBleue>1234567890123456</hot:numeroCarteBleue>
         </hot:client>
         <hot:chambreId>2</hot:chambreId>
         <hot:dateArrive>2025-12-10</hot:dateArrive>
         <hot:dateDepart>2025-12-15</hot:dateDepart>
      </hot:effectuerReservationRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Response:**
```xml
<hot:effectuerReservationResponse>
    <hot:reservationId>1</hot:reservationId>
    <hot:success>true</hot:success>
    <hot:message>Réservation effectuée avec succès</hot:message>
</hot:effectuerReservationResponse>
```

### 4. getReservations
Obtenir toutes les réservations

**Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:getReservationsRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Response:**
```xml
<hot:getReservationsResponse>
    <hot:reservations>
        <hot:id>1</hot:id>
        <hot:client>...</hot:client>
        <hot:chambre>...</hot:chambre>
        <hot:dateArrive>2025-12-10</hot:dateArrive>
        <hot:dateDepart>2025-12-15</hot:dateDepart>
    </hot:reservations>
</hot:getReservationsResponse>
```

## Démarrage

### Option 1 : Maven
```bash
cd /home/etudiant/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```

### Option 2 : Script
```bash
cd /home/etudiant/Bureau/SoapRepository/Hotellerie
./start-hotel.sh
```

L'application démarre sur **http://localhost:8082**

## Tests

### Accéder au WSDL
```bash
curl http://localhost:8082/ws/hotel.wsdl
```

### Script de test automatisé
```bash
./test-soap.sh
```

### Test manuel avec curl

**Exemple : Rechercher des chambres**
```bash
curl -X POST http://localhost:8082/ws \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:rechercherChambresRequest>
         <hot:adresse>Paris</hot:adresse>
         <hot:dateArrive>2025-12-01</hot:dateArrive>
         <hot:dateDepart>2025-12-05</hot:dateDepart>
         <hot:prixMin>0</hot:prixMin>
         <hot:prixMax>200</hot:prixMax>
      </hot:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Structure du Projet

```
Hotellerie/
├── pom.xml                                # Dépendances SOAP ajoutées
├── start-hotel.sh                         # Script de démarrage
├── test-soap.sh                           # Script de test
│
├── src/main/java/org/tp1/hotellerie/
│   ├── model/                             # Modèles existants
│   │   ├── Client.java
│   │   ├── Chambre.java
│   │   ├── Hotel.java
│   │   ├── Reservation.java
│   │   └── Type.java
│   │
│   └── soap/                              # Nouveaux composants SOAP
│       ├── HotelEndpoint.java             # Endpoints SOAP
│       ├── HotelService.java              # Service métier
│       ├── WebServiceConfig.java          # Configuration Spring WS
│       └── generated/                     # Classes générées depuis XSD
│
└── src/main/resources/
    ├── application.properties             # Port 8082
    └── xsd/
        └── hotel.xsd                      # Schéma SOAP
```

## Données de Test

L'hôtel est initialisé avec :
- **Nom** : Grand Hotel Paris
- **Adresse** : 10 Rue de la Paix, Paris
- **Type** : CAT5 (5 étoiles)
- **Chambres** :
  1. Chambre Simple - 80€ - 1 lit
  2. Chambre Double - 120€ - 2 lits
  3. Suite Deluxe - 200€ - 3 lits
  4. Chambre Familiale - 150€ - 4 lits
  5. Chambre Economy - 60€ - 1 lit

## Dépendances Ajoutées

- `spring-boot-starter-web-services` - Serveur SOAP
- `wsdl4j` - Génération WSDL
- `jaxb-api`, `jaxb-impl`, `jaxb-core` - XML binding (Java 8)
- `maven-jaxb2-plugin` - Génération classes depuis XSD

## Prochaines Étapes

1. **Connecter l'Agence à ce service SOAP**
   - Copier le WSDL dans le projet Agence
   - Générer les classes clientes
   - Implémenter les appels SOAP réels

2. **Créer d'autres hôtels**
   - Dupliquer le projet sur d'autres ports (8083, 8084...)
   - Modifier les données de test

3. **Ajouter des fonctionnalités**
   - Annulation de réservation
   - Modification de réservation
   - Gestion des disponibilités

## Troubleshooting

**Port 8082 déjà utilisé**
```bash
lsof -i :8082
kill -9 <PID>
# OU modifier application.properties
```

**WSDL non accessible**
```bash
# Vérifier que l'application tourne
curl http://localhost:8082/ws/hotel.wsdl
# Si erreur, redémarrer l'application
```

**Erreur de compilation**
```bash
mvn clean install
```

## Ressources

- **WSDL** : http://localhost:8082/ws/hotel.wsdl
- **Endpoint** : http://localhost:8082/ws
- **Namespace** : http://tp1.org/hotellerie/soap

