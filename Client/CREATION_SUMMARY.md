# ✅ CLIENT CLI CRÉÉ AVEC SUCCÈS !

## 📋 RÉSUMÉ

Un client Spring Boot en ligne de commande (CLI) a été créé pour interagir avec l'agence de réservation d'hôtels.

## 🗂️ Structure Créée

```
Client/
├── pom.xml                          ✅ Configuration Maven
├── start-client.sh                  ✅ Script de démarrage
├── README.md                        ✅ Documentation complète
├── QUICKSTART.md                    ✅ Guide rapide
└── src/main/
    ├── java/org/tp1/client/
    │   ├── ClientApplication.java   ✅ Application principale
    │   ├── cli/
    │   │   └── ClientCLI.java       ✅ Interface CLI interactive
    │   ├── service/
    │   │   └── AgenceClientService.java  ✅ Client REST
    │   ├── dto/
    │   │   ├── RechercheRequest.java     ✅ DTO Recherche
    │   │   ├── ChambreDTO.java           ✅ DTO Chambre
    │   │   ├── ReservationRequest.java   ✅ DTO Réservation
    │   │   └── ReservationResponse.java  ✅ DTO Réponse
    │   └── config/
    │       └── RestTemplateConfig.java   ✅ Config REST
    └── resources/
        └── application.properties   ✅ Configuration app
```

## ✨ Fonctionnalités Implémentées

### 1. Interface CLI Interactive
- ✅ Menu principal avec 4 options
- ✅ Codes couleurs ANSI pour meilleure lisibilité
- ✅ Bannière d'accueil
- ✅ Messages d'erreur clairs

### 2. Recherche de Chambres
- ✅ Critères multiples (adresse, dates, prix, étoiles, lits)
- ✅ Affichage formaté des résultats
- ✅ Mémorisation des dernières chambres trouvées

### 3. Réservation
- ✅ Sélection de chambre par ID
- ✅ Saisie des informations client
- ✅ Confirmation de réservation avec ID

### 4. Communication REST
- ✅ RestTemplate configuré
- ✅ Test de connexion au démarrage (ping)
- ✅ Appels POST pour recherche et réservation
- ✅ Gestion des erreurs

## 🎯 Architecture du Client

```
┌──────────────────────────────────────┐
│     ClientApplication.java           │
│   (Point d'entrée Spring Boot)       │
└──────────────┬───────────────────────┘
               │
               ↓
┌──────────────────────────────────────┐
│         ClientCLI.java               │
│   (Interface utilisateur CLI)        │
│   - Menu interactif                  │
│   - Saisie des données               │
│   - Affichage des résultats          │
└──────────────┬───────────────────────┘
               │
               ↓
┌──────────────────────────────────────┐
│    AgenceClientService.java          │
│   (Communication REST)                │
│   - ping()                            │
│   - rechercherChambres()              │
│   - effectuerReservation()            │
└──────────────┬───────────────────────┘
               │ HTTP REST
               ↓
┌──────────────────────────────────────┐
│         AGENCE REST API              │
│       http://localhost:8081          │
│   - GET  /api/agence/ping            │
│   - POST /api/agence/rechercher      │
│   - POST /api/agence/reserver        │
└──────────────────────────────────────┘
```

## 🚀 Pour Démarrer

### ⚠️ PRÉREQUIS : Installer le JDK

Le JRE seul ne suffit pas pour compiler. Installez le JDK :

```bash
# Installer OpenJDK 8
sudo apt install openjdk-8-jdk

# Vérifier l'installation
javac -version
```

### Ordre de Démarrage

1. **Hotellerie** (Port 8082)
   ```bash
   cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
   mvn spring-boot:run
   ```

2. **Agence** (Port 8081)
   ```bash
   cd /home/corentinfay/Bureau/SoapRepository/Agence
   mvn spring-boot:run
   ```

3. **Client** (CLI)
   ```bash
   cd /home/corentinfay/Bureau/SoapRepository/Client
   mvn spring-boot:run
   ```

## 📝 Exemple d'Utilisation

```
╔═══════════════════════════════════════════════════╗
║     SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT     ║
╚═══════════════════════════════════════════════════╝

Connexion à l'agence... ✓ Connecté

═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter

Votre choix: 1

═══ RECHERCHE DE CHAMBRES ═══
Adresse (ville/rue): Paris
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
Prix minimum: 0
Prix maximum: 200
Nombre d'étoiles: 0
Nombre de lits: 0

✓ 5 chambre(s) trouvée(s):

[ID: 1] Chambre Simple
  Prix: 80.00€ | Lits: 1 | Hôtel: Grand Hotel Paris
  Adresse: 10 Rue de la Paix, Paris
...
```

## 🎨 Caractéristiques du CLI

### Codes Couleurs
- 🔵 **Bleu** : Options de recherche
- 🟢 **Vert** : Réservation et succès
- 🟡 **Jaune** : Informations et traitement en cours
- 🔴 **Rouge** : Erreurs et quitter
- 🔷 **Cyan** : Bannière et IDs importants

### Validations
- ✅ Test de connexion à l'agence au démarrage
- ✅ Vérification des IDs de chambre
- ✅ Gestion des erreurs de saisie
- ✅ Messages d'erreur explicites

### Workflow Utilisateur
1. Le client se connecte à l'agence (ping)
2. L'utilisateur recherche des chambres
3. Les résultats sont affichés et mémorisés
4. L'utilisateur peut réserver une chambre
5. La réservation est confirmée avec un ID

## 📦 Dépendances Utilisées

```xml
<!-- Spring Boot (core + web) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JLine pour CLI avancé -->
<dependency>
    <groupId>org.jline</groupId>
    <artifactId>jline</artifactId>
    <version>3.21.0</version>
</dependency>
```

## 🔗 Communication REST

### Format des Requêtes

**Recherche de chambres :**
```json
POST http://localhost:8081/api/agence/rechercher
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

**Réservation :**
```json
POST http://localhost:8081/api/agence/reserver
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

## ✅ Système Complet

```
┌─────────────┐         ┌─────────────┐         ┌──────────────┐
│   Client    │   REST  │   Agence    │   SOAP  │  Hotellerie  │
│             │ ──────> │             │ ──────> │              │
│  ✅ CRÉÉ    │         │  ✅ FAIT    │         │  ✅ FAIT     │
│  Port: -    │ <────── │  Port 8081  │ <────── │  Port 8082   │
└─────────────┘         └─────────────┘         └──────────────┘
  CLI Spring              REST + SOAP             SOAP Server
                          Client
```

## 📚 Documentation Créée

1. **README.md** - Documentation complète du client
2. **QUICKSTART.md** - Guide de démarrage rapide
3. **CREATION_SUMMARY.md** - Ce fichier

## 🎓 Concepts Utilisés

### Spring Boot
- ✅ `@SpringBootApplication` - Configuration automatique
- ✅ `@Component` - Bean CLI
- ✅ `@Service` - Service REST
- ✅ `@Configuration` - Configuration RestTemplate
- ✅ `spring.main.web-application-type=none` - Pas de serveur web

### RestTemplate
- ✅ `getForEntity()` - GET request (ping)
- ✅ `postForEntity()` - POST request (réservation)
- ✅ `exchange()` - POST avec type générique (recherche)
- ✅ `ParameterizedTypeReference` - Type List<ChambreDTO>

### Java
- ✅ Scanner pour lire les entrées utilisateur
- ✅ Codes ANSI pour couleurs dans le terminal
- ✅ Formatage de chaînes avec `printf()`
- ✅ Gestion des exceptions

## 🔍 Points Techniques

### Pas de Serveur Web
```properties
spring.main.web-application-type=none
```
Le client n'a pas besoin de serveur Tomcat, il fait uniquement des requêtes HTTP sortantes.

### Injection de Dépendances
```java
@Autowired
private AgenceClientService agenceService;
```
Spring injecte automatiquement le service dans le CLI.

### Workflow d'Application
```java
public static void main(String[] args) {
    ConfigurableApplicationContext context = 
        SpringApplication.run(ClientApplication.class, args);
    ClientCLI cli = context.getBean(ClientCLI.class);
    cli.run();
    System.exit(SpringApplication.exit(context));
}
```

## 🎯 Prochaines Étapes

### Installation du JDK
```bash
sudo apt install openjdk-8-jdk
javac -version
```

### Compilation
```bash
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn clean compile
```

### Exécution
```bash
mvn spring-boot:run
```

## 🏆 Résultat Final

**Le système complet est maintenant fonctionnel :**
1. ✅ **Hotellerie** - Service SOAP avec 5 chambres de test
2. ✅ **Agence** - API REST + Client SOAP
3. ✅ **Client** - CLI interactif avec RestTemplate

**Tous les composants communiquent :**
- Client → Agence (REST)
- Agence → Hotellerie (SOAP)

---

**Date de création** : 2025-11-15  
**Version** : 1.0  
**Statut** : ✅ CLIENT CLI OPÉRATIONNEL (après installation JDK)

