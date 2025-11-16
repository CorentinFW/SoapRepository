# Transformation Complète : REST → SOAP

## ✅ Résumé de la transformation

Votre système de réservation hôtelière a été **complètement transformé** pour éliminer REST et utiliser **uniquement SOAP** pour toutes les communications.

## Architecture Finale

```
┌──────────────────┐
│     CLIENT       │
│   (CLI SOAP)     │
│                  │
└────────┬─────────┘
         │ SOAP
         ▼
┌──────────────────┐
│     AGENCE       │
│  (Serveur SOAP)  │
│  (Client SOAP)   │
│   Port 8081      │
└────────┬─────────┘
         │ SOAP
         ▼
┌──────────────────┐
│   HOTELLERIE     │
│  (Serveur SOAP)  │
│   Port 8082      │
└──────────────────┘
```

## Modifications Effectuées

### 1. AGENCE (Serveur SOAP)

#### ✅ Fichiers Créés/Modifiés :
- **Créé** : `src/main/resources/xsd/agence.xsd` - Définition du contrat SOAP
- **Créé** : `src/main/java/org/tp1/agence/config/AgenceWebServiceConfig.java` - Configuration SOAP
- **Créé** : `src/main/java/org/tp1/agence/endpoint/AgenceEndpoint.java` - Endpoint SOAP
- **Supprimé** : `src/main/java/org/tp1/agence/controller/AgenceController.java` - (REST)
- **Modifié** : `pom.xml` - Supprimé `spring-boot-starter-web`, ajouté plugin JAXB

#### Services SOAP Exposés :
1. **pingRequest** → Vérifier la disponibilité
2. **rechercherChambresRequest** → Rechercher des chambres
3. **effectuerReservationRequest** → Faire une réservation

#### WSDL Disponible :
```
http://localhost:8081/ws/agence.wsdl
```

### 2. CLIENT (Client SOAP)

#### ✅ Fichiers Créés/Modifiés :
- **Créé** : `src/main/resources/wsdl/agence.wsdl` - WSDL de l'Agence
- **Créé** : `src/main/java/org/tp1/client/soap/AgenceSoapClient.java` - Client SOAP
- **Créé** : `src/main/java/org/tp1/client/config/SoapClientConfig.java` - Configuration SOAP
- **Créé** : `src/main/java/org/tp1/client/cli/ClientCLISoap.java` - CLI utilisant SOAP
- **Supprimé** : Tous les DTOs REST (`dto/`)
- **Supprimé** : `service/AgenceClientService.java` (REST)
- **Supprimé** : `config/RestTemplateConfig.java`
- **Supprimé** : `cli/ClientCLI.java` (ancien REST)
- **Modifié** : `ClientApplication.java` - Utilise `ClientCLISoap`
- **Modifié** : `pom.xml` - Supprimé `spring-boot-starter-web`, ajouté Web Services
- **Modifié** : `application.properties` - `agence.soap.url` au lieu de `agence.url`

#### Classes Générées Automatiquement :
- `target/generated-sources/xjc/org/tp1/client/wsdl/agence/*` - Classes JAXB générées depuis le WSDL

### 3. HOTELLERIE (Déjà SOAP)

Aucune modification - déjà configuré en SOAP ✅

## Utilisation

### Démarrage Rapide

```bash
cd /home/corentinfay/Bureau/SoapRepository
./start-system-soap.sh
```

Ce script :
1. Démarre **Hotellerie** (Port 8082)
2. Démarre **Agence** (Port 8081)
3. Démarre le **Client CLI**

### Démarrage Manuel

#### Terminal 1 - Hotellerie
```bash
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```

#### Terminal 2 - Agence
```bash
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn spring-boot:run
```

#### Terminal 3 - Client
```bash
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

## Menu du Client CLI

```
╔═══════════════════════════════════════════════════╗
║                                                   ║
║   SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT SOAP  ║
║                                                   ║
╚═══════════════════════════════════════════════════╝

Connexion à l'agence SOAP... ✓ Connecté - Agence SOAP opérationnelle

═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter
```

## Tests SOAP avec curl

### Test Ping
```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                         xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:pingRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

### Test Recherche
```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                         xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:rechercherChambresRequest>
         <soap:dateArrive>2025-12-01</soap:dateArrive>
         <soap:dateDepart>2025-12-05</soap:dateDepart>
         <soap:prixMax>200</soap:prixMax>
      </soap:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Vérifications

### ✅ WSDL Accessibles

- **Agence** : `curl http://localhost:8081/ws/agence.wsdl`
- **Hotellerie** : `curl http://localhost:8082/ws/hotel.wsdl`

### ✅ Aucun endpoint REST

Tous les endpoints REST ont été supprimés. Seul SOAP est utilisé.

### ✅ Configuration

#### Agence - application.properties
```properties
server.port=8081
spring.application.name=Agence
hotel.soap.url=http://localhost:8082/ws
```

#### Client - application.properties
```properties
server.port=8083
spring.application.name=Client
spring.main.web-application-type=none
agence.soap.url=http://localhost:8081/ws
```

## Dépendances Maven

### Agence & Client
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web-services</artifactId>
</dependency>

<!-- JAXB pour Java 8 -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
```

## Technologies Utilisées

- **Spring Boot 2.7.18**
- **Spring Web Services** (SOAP)
- **JAXB 2.3.x** (Marshalling/Unmarshalling XML)
- **Maven JAXB Plugin** (Génération de classes)
- **Java 8**

## Résultat Final

✅ **100% SOAP** - Aucun REST dans le projet  
✅ **Client CLI** - Interface en ligne de commande  
✅ **3 Services** - Hotellerie, Agence, Client  
✅ **Architecture cohérente** - SOAP de bout en bout  

## Fichiers de Documentation

- `README_SOAP.md` - Documentation complète du système
- `TRANSFORMATION_SOAP.md` - Ce fichier
- `start-system-soap.sh` - Script de démarrage

---

**Date de transformation** : 16 Novembre 2025  
**Architecture** : SOAP pur (SpringBoot + Web Services)  
**Communication** : Client SOAP ↔ Agence SOAP ↔ Hotellerie SOAP

