# 🏨 Système de Réservation Hôtelière - Architecture SOAP

## 📋 Description

Système distribué de réservation d'hôtels basé sur l'architecture SOAP. Le projet permet à un client de rechercher et réserver des chambres via une agence qui interroge plusieurs hôtels.

### Architecture du système

```
┌─────────────┐
│   CLIENT    │  (Port: CLI)
│  (Spring)   │  Interface en ligne de commande
└──────┬──────┘
       │ SOAP
       ↓
┌─────────────┐
│   AGENCE    │  (Port: 8081)
│  (Spring)   │  Agrège les résultats des hôtels
└──────┬──────┘
       │ SOAP
       ├─────────────┬─────────────┐
       ↓             ↓             ↓
┌──────────┐  ┌──────────┐  ┌──────────┐
│  HÔTEL   │  │  HÔTEL   │  │  HÔTEL   │
│  Paris   │  │  Lyon    │  │Montpellier│
│ (8082)   │  │ (8083)   │  │  (8084)  │
└──────────┘  └──────────┘  └──────────┘
```

### Flux de communication

1. **Client → Agence** : Le client envoie une requête SOAP (recherche ou réservation)
2. **Agence → Hôtels** : L'agence interroge tous les hôtels disponibles via SOAP
3. **Hôtels → Agence** : Chaque hôtel répond avec ses disponibilités
4. **Agence → Client** : L'agence agrège et renvoie les résultats au client

---

## 🚀 Démarrage Rapide

### Prérequis

- **Java 17** ou supérieur
- **Maven 3.6+**
- **Ports disponibles** : 8081, 8082, 8083, 8084

### Option 1 : Démarrage automatique (Recommandé)

#### Avec script robuste (recommandé)
```bash
./start-robuste.sh
```
Ce script :
- Démarre les 3 hôtelleries (Paris, Lyon, Montpellier)
- Attend que chaque service soit prêt
- Démarre l'agence
- Lance le client CLI
- Nettoie automatiquement à l'arrêt (Ctrl+C)

#### Test rapide
```bash
./premier-test.sh
```
Guide interactif pour votre premier test du système.

### Option 2 : Démarrage manuel

#### 1. Démarrer les hôtelleries

**Terminal 1 - Hôtel Paris (port 8082)** :
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris
```

**Terminal 2 - Hôtel Lyon (port 8083)** :
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=lyon
```

**Terminal 3 - Hôtel Montpellier (port 8084)** :
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier
```

⏱️ **Attendre 30-60 secondes** que chaque hôtel soit complètement démarré.

#### 2. Démarrer l'agence

**Terminal 4 - Agence (port 8081)** :
```bash
cd Agence
mvn spring-boot:run
```

⏱️ **Attendre 30-60 secondes** que l'agence soit prête.

#### 3. Démarrer le client

**Terminal 5 - Client CLI** :
```bash
cd Client
mvn spring-boot:run
```

Le client démarre automatiquement l'interface en ligne de commande.

---

## 📂 Structure du Projet

```
SoapRepository/
├── Hotellerie/          # Service SOAP des hôtels
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── org/tp1/hotellerie/
│   │       │       ├── soap/          # Endpoint SOAP
│   │       │       ├── service/       # Logique métier
│   │       │       └── model/         # Modèle de données
│   │       └── resources/
│   │           ├── application-paris.properties
│   │           ├── application-lyon.properties
│   │           ├── application-montpellier.properties
│   │           ├── wsdl/              # Contrat WSDL
│   │           └── xsd/               # Schémas XML
│   └── README.md
│
├── Agence/              # Service SOAP de l'agence
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── org/tp1/agence/
│   │       │       ├── endpoint/      # Endpoint SOAP (serveur)
│   │       │       ├── client/        # Clients SOAP (vers hôtels)
│   │       │       ├── service/       # Logique d'agrégation
│   │       │       └── dto/           # Objets de transfert
│   │       └── resources/
│   │           ├── application.properties
│   │           └── wsdl/              # WSDL de l'agence et des hôtels
│   └── README.md
│
├── Client/              # Application cliente CLI
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── org/tp1/client/
│   │       │       ├── cli/           # Interface ligne de commande
│   │       │       ├── soap/          # Client SOAP (vers agence)
│   │       │       └── wsdl/          # Classes générées
│   │       └── resources/
│   │           └── application.properties
│   └── README.md
│
├── Image/               # Images des hôtels
├── ALLReadme/           # Archive des anciens README et documentation
├── Rapport/             # Rapport technique du projet
└── *.sh                 # Scripts de démarrage et tests
```

---

## 🔧 Technologies Utilisées

- **Java 17** - Langage de programmation
- **Spring Boot 2.7.18** - Framework d'application
- **Spring Web Services (Spring-WS)** - Implémentation SOAP
- **JAXB** - Génération de classes à partir de XSD
- **Maven** - Gestion des dépendances et build
- **SOAP/WSDL** - Protocole de communication entre services

---

## 📖 Fonctionnalités

### Client CLI
- Recherche de chambres disponibles (par ville, dates, prix, nombre de lits)
- Réservation de chambres avec validation des dates
- Affichage des images des hôtels (URL localhost)
- Consultation des réservations par hôtel

### Agence
- Agrégation des résultats de plusieurs hôtels
- Routage des requêtes vers les hôtels appropriés
- Gestion des réservations multi-hôtels
- Exposition d'une API SOAP unifiée pour le client

### Hôtellerie
- Gestion des chambres et disponibilités
- Validation des dates de réservation (pas de chevauchement)
- Génération d'ID uniques pour les réservations
- Données en mémoire initialisées au démarrage
- Support multi-instances (Paris, Lyon, Montpellier)

---

## 🧪 Tests

### Test complet du système
```bash
./start-robuste.sh
```

### Test avec un seul hôtel (Paris)
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris

# Dans un autre terminal
cd Agence
mvn spring-boot:run

# Dans un troisième terminal
cd Client
mvn spring-boot:run
```

### Vérification des endpoints SOAP

**Hôtel Paris** :
```bash
curl http://localhost:8082/ws?wsdl
```

**Agence** :
```bash
curl http://localhost:8081/ws?wsdl
```

---

## 📝 Utilisation du Client CLI

Une fois le client démarré, vous verrez le menu principal :

```
═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Afficher toutes les réservations par hôtel
5. Quitter
```

### Exemple de recherche
```
Votre choix: 1
Ville: Paris
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
Prix minimum (ou entrée pour ignorer): 50
Prix maximum (ou entrée pour ignorer): 150
Nombre d'étoiles (ou entrée pour ignorer): 
Nombre de lits (ou entrée pour ignorer): 2
```

### Exemple de réservation
```
Votre choix: 2
Numéro de chambre: 101
Date d'arrivée (YYYY-MM-DD): 2025-12-01
Date de départ (YYYY-MM-DD): 2025-12-05
Nom: Dupont
Prénom: Jean
Numéro de carte bancaire: 1234567890123456
```

---

## ⚠️ Résolution de Problèmes

### Erreur : "Connexion refusée"
- Vérifiez que tous les services sont démarrés dans le bon ordre
- Attendez 30-60 secondes après chaque démarrage
- Utilisez `./start-robuste.sh` qui gère automatiquement les délais

### Erreur : "Port already in use"
- Un service est déjà en cours d'exécution sur le port
- Arrêtez tous les services : `pkill -f "spring-boot:run"`
- Relancez le système

### Aucune chambre trouvée
- Vérifiez que les hôtels sont bien démarrés
- Les données sont initialisées au démarrage de chaque hôtel
- Consultez les logs des hôtels pour voir les chambres disponibles

### Réservation affiche "Mauvaise date"
- Les dates demandées chevauchent une réservation existante
- Essayez d'autres dates ou une autre chambre

---

## 📚 Documentation Détaillée

- **[Hotellerie/README.md](Hotellerie/README.md)** - Documentation du service hôtelier
- **[Agence/README.md](Agence/README.md)** - Documentation du service agence
- **[Client/README.md](Client/README.md)** - Documentation du client CLI
- **[Rapport/rapportV1.txt](Rapport/rapportV1.txt)** - Rapport technique complet

---

## 👥 Auteurs

Projet développé dans le cadre d'un TP sur les architectures distribuées et SOAP.

---

## 📄 Licence

Projet académique - Usage éducatif uniquement.

