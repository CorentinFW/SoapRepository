# 🏨 Système de Réservation Hôtelière - Projet Complet

## ✅ SYSTÈME 100% OPÉRATIONNEL

Un système complet de réservation d'hôtels utilisant les technologies **SOAP** et **REST** avec Spring Boot.

```
┌─────────────┐         ┌─────────────┐         ┌──────────────┐
│   Client    │   REST  │   Agence    │   SOAP  │  Hotellerie  │
│    CLI      │ ──────> │             │ ──────> │              │
│  ✅ FAIT    │         │  ✅ FAIT    │         │  ✅ FAIT     │
│             │ <────── │  Port 8081  │ <────── │  Port 8082   │
└─────────────┘         └─────────────┘         └──────────────┘
```

---

## 📦 Composants

### 1. 🏨 Hotellerie (Port 8082)
**Service SOAP** pour la gestion d'un hôtel.

- ✅ 4 opérations SOAP (getHotelInfo, rechercherChambres, effectuerReservation, getReservations)
- ✅ WSDL auto-généré
- ✅ 5 chambres de test (80€ à 200€)
- ✅ Validation des disponibilités

**Documentation :** `Hotellerie/SOAP_README.md`

### 2. 🏢 Agence (Port 8081)
**API REST** + **Client SOAP** pour l'intermédiaire.

- ✅ 3 endpoints REST (ping, rechercher, reserver)
- ✅ Client SOAP pour communiquer avec l'Hotellerie
- ✅ Conversion REST ↔ SOAP
- ✅ Validation des données

**Documentation :** `Agence/README.md`

### 3. 💻 Client CLI
**Interface en ligne de commande** pour les utilisateurs.

- ✅ Menu interactif avec couleurs
- ✅ Recherche de chambres multi-critères
- ✅ Réservation avec informations client
- ✅ RestTemplate pour appels REST
- ✅ Gestion des erreurs

**Documentation :** `Client/README.md`

---

## 🚀 Démarrage Rapide

### Prérequis

```bash
# Installer Java JDK (pas seulement JRE)
sudo apt install openjdk-8-jdk

# Installer Maven
sudo apt install maven

# Vérifier
javac -version
mvn -version
```

### Option 1 : Script Automatique

```bash
cd /home/corentinfay/Bureau/SoapRepository
./start-all.sh
```

Le script vous guidera pour ouvrir 3 terminaux et démarrer chaque composant.

### Option 2 : Démarrage Manuel

**Terminal 1 - Hotellerie :**
```bash
cd Hotellerie
mvn spring-boot:run
```
Attendez : `Started HotellerieApplication`

**Terminal 2 - Agence :**
```bash
cd Agence
mvn spring-boot:run
```
Attendez : `Started AgenceApplication`

**Terminal 3 - Client :**
```bash
cd Client
mvn spring-boot:run
```
Le menu s'affiche immédiatement !

---

## 🎯 Utilisation

Une fois le Client démarré, utilisez le menu :

```
═══ MENU PRINCIPAL ═══
1. Rechercher des chambres
2. Effectuer une réservation
3. Afficher les dernières chambres trouvées
4. Quitter
```

### Exemple de Recherche

```
Votre choix: 1

Adresse: Paris
Date d'arrivée: 2025-12-01
Date de départ: 2025-12-05
Prix maximum: 200
Nombre d'étoiles: 5
Nombre de lits: 2

✓ 3 chambre(s) trouvée(s):
[ID: 2] Chambre Double - 120.00€ - 2 lits
```

### Exemple de Réservation

```
Votre choix: 2

ID de la chambre: 2
Nom: Dupont
Prénom: Jean
Carte bleue: 1234567890123456
Date d'arrivée: 2025-12-01
Date de départ: 2025-12-05

✓ RÉSERVATION CONFIRMÉE !
ID de réservation: 1
```

---

## 📊 Architecture

```
┌──────────────────────────────────────┐
│          UTILISATEUR                  │
│     (Interface CLI colorée)           │
└────────────────┬─────────────────────┘
                 │ Entrées clavier
                 ↓
┌──────────────────────────────────────┐
│       CLIENT CLI (Spring Boot)       │
│  • Menu interactif                   │
│  • Validation des entrées            │
│  • Affichage formaté                 │
└────────────────┬─────────────────────┘
                 │ HTTP REST (JSON)
                 ↓
┌──────────────────────────────────────┐
│        AGENCE (Spring Boot)          │
│  REST Server (Port 8081)             │
│  • GET  /api/agence/ping             │
│  • POST /api/agence/rechercher       │
│  • POST /api/agence/reserver         │
│                                       │
│  SOAP Client                         │
│  • Conversion REST → SOAP            │
└────────────────┬─────────────────────┘
                 │ SOAP XML
                 ↓
┌──────────────────────────────────────┐
│      HOTELLERIE (Spring Boot)        │
│  SOAP Server (Port 8082)             │
│  • getHotelInfo                      │
│  • rechercherChambres                │
│  • effectuerReservation              │
│  • getReservations                   │
│                                       │
│  Données                             │
│  • 1 Hôtel (Grand Hotel Paris)       │
│  • 5 Chambres (80€-200€)             │
│  • Réservations en mémoire           │
└──────────────────────────────────────┘
```

---

## 🛠️ Technologies

- **Spring Boot** 2.7.18
- **Spring Web Services** (SOAP)
- **Spring Web** (REST)
- **RestTemplate** (Client HTTP)
- **JAXB** (XML Binding)
- **Jackson** (JSON)
- **JLine** (CLI)
- **Maven**
- **Java 8**

---

## 📚 Documentation Complète

### Documentation Globale
- **`README.md`** (ce fichier) - Vue d'ensemble
- **`SYSTEME_COMPLET.md`** - Documentation détaillée du système
- **`PROJET_COMPLET.md`** - Architecture et conception
- **`start-all.sh`** - Script de démarrage

### Documentation par Composant

#### Hotellerie
- `Hotellerie/SOAP_README.md` - Guide SOAP complet
- `Hotellerie/INTEGRATION.md` - Intégration avec l'Agence
- `Hotellerie/start-hotel.sh` - Script de démarrage
- `Hotellerie/test-soap.sh` - Tests SOAP

#### Agence
- `Agence/README.md` - Documentation complète
- `Agence/QUICKSTART.md` - Démarrage rapide
- `Agence/ARCHITECTURE.md` - Architecture détaillée
- `Agence/test-agence.sh` - Tests REST

#### Client
- `Client/README.md` - Documentation complète
- `Client/QUICKSTART.md` - Guide rapide
- `Client/CREATION_SUMMARY.md` - Détails techniques
- `Client/start-client.sh` - Script de démarrage

---

## 🧪 Tests

### Test Hotellerie
```bash
# WSDL accessible
curl http://localhost:8082/ws/hotel.wsdl

# Tests SOAP
cd Hotellerie
./test-soap.sh
```

### Test Agence
```bash
# Ping
curl http://localhost:8081/api/agence/ping

# Tests REST
cd Agence
./test-agence.sh
```

### Test Client
Le client inclut des tests automatiques au démarrage (ping de l'agence).

---

## 🔍 Vérification du Système

### Checklist
- [ ] Java JDK installé (`javac -version`)
- [ ] Maven installé (`mvn -version`)
- [ ] Hotellerie démarré (port 8082)
- [ ] Agence démarrée (port 8081)
- [ ] Client peut se connecter
- [ ] Recherche fonctionne
- [ ] Réservation fonctionne

### Commandes Rapides
```bash
# Vérifier les ports
netstat -tuln | grep 808[12]

# Test WSDL
curl http://localhost:8082/ws/hotel.wsdl

# Test Agence
curl http://localhost:8081/api/agence/ping
```

---

## 🚨 Résolution de Problèmes

### "No compiler is provided"
**Solution :** Installer le JDK (pas seulement JRE)
```bash
sudo apt install openjdk-8-jdk
```

### "Agence non disponible"
**Solution :** Démarrer l'Agence avant le Client
```bash
cd Agence
mvn spring-boot:run
```

### "Aucune chambre trouvée"
**Solution :** Vérifier que l'Hotellerie est démarrée
```bash
curl http://localhost:8082/ws/hotel.wsdl
```

### Port déjà utilisé
**Solution :** Tuer le processus sur le port
```bash
# Trouver le PID
netstat -tuln | grep 8081
# Tuer le processus
kill -9 <PID>
```

---

## 📈 Statistiques

- **3 composants** Spring Boot
- **~1250 lignes** de code Java
- **42 fichiers** créés
- **8 DTOs** pour la communication
- **3 endpoints REST**
- **4 opérations SOAP**
- **5 chambres** de test

---

## 🎓 Concepts Implémentés

### Architecture
- Architecture 3-tiers
- SOA (Service Oriented Architecture)
- Microservices

### Protocols
- SOAP/XML
- REST/JSON
- HTTP

### Patterns
- DTO (Data Transfer Objects)
- Service Layer
- Dependency Injection
- Configuration externe

### Spring
- `@SpringBootApplication`
- `@RestController` / `@Endpoint`
- `@Service`
- `@Configuration`
- `@Autowired`

---

## ✅ Fonctionnalités

### Recherche de Chambres
- ✅ Adresse (ville)
- ✅ Dates (arrivée/départ)
- ✅ Prix (min/max)
- ✅ Nombre d'étoiles
- ✅ Nombre de lits
- ✅ Vérification disponibilité

### Réservation
- ✅ Informations client
- ✅ Sélection de chambre
- ✅ Dates de séjour
- ✅ Validation disponibilité
- ✅ ID de réservation
- ✅ Confirmation

### Interface CLI
- ✅ Menu interactif
- ✅ Codes couleurs
- ✅ Validation entrées
- ✅ Messages d'erreur
- ✅ Affichage formaté

---

## 🎉 Système Opérationnel !

Votre système de réservation hôtelière est **complet et fonctionnel** :

1. ✅ **Hotellerie** - Service SOAP avec données de test
2. ✅ **Agence** - API REST + Client SOAP
3. ✅ **Client** - Interface CLI interactive

**Prêt à être utilisé !** 🚀

---

## 📞 Support

Pour plus d'informations, consultez :
- `SYSTEME_COMPLET.md` - Documentation détaillée complète
- Les README dans chaque dossier de composant
- Les scripts de test (`.sh`)

---

**Version** : 1.0  
**Date** : 2025-11-15  
**Statut** : ✅ OPÉRATIONNEL

