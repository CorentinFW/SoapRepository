# 🚀 GUIDE DE DÉMARRAGE RAPIDE - CLIENT CLI

## ⚡ Démarrage Rapide (5 minutes)

### Étape 1 : Vérifier les prérequis

```bash
# Vérifier Java
java -version

# Vérifier Maven
mvn -version
```

### Étape 2 : Démarrer les services (dans l'ordre)

**Terminal 1 - Hotellerie (SOAP Server)**
```bash
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```
Attendez le message : `Started HotellerieApplication`

**Terminal 2 - Agence (REST Server + SOAP Client)**
```bash
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn spring-boot:run
```
Attendez le message : `Started AgenceApplication`

**Terminal 3 - Client (CLI)**
```bash
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

### Étape 3 : Utiliser le Client

1. **Rechercher des chambres** (option 1)
   - Adresse : `Paris`
   - Date arrivée : `2025-12-01`
   - Date départ : `2025-12-05`
   - Prix max : `200`
   - Autres : `0` pour ignorer

2. **Effectuer une réservation** (option 2)
   - Choisir un ID de chambre
   - Entrer vos informations
   - Confirmer

3. **Quitter** (option 4)

## 🎯 Exemple d'Utilisation

```
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
Nombre d'étoiles (1-6) [optionnel, 0 pour ignorer]: 0
Nombre de lits minimum [optionnel, 0 pour ignorer]: 0

Recherche en cours...
✓ 5 chambre(s) trouvée(s)
```

## 🔧 Scripts Disponibles

### Démarrage
```bash
./start-client.sh
```

### Compilation
```bash
mvn clean compile
```

### Empaquetage
```bash
mvn clean package
java -jar target/Client-0.0.1-SNAPSHOT.jar
```

## 🚨 Résolution de Problèmes

### Le client ne peut pas se connecter à l'agence
```
✗ Échec - L'agence n'est pas disponible
```
**Solution** : Vérifiez que l'Agence est démarrée sur le port 8081
```bash
curl http://localhost:8081/api/agence/ping
```

### Aucune chambre trouvée
**Solution** : Vérifiez que l'Hotellerie est démarrée et que l'Agence peut y accéder
```bash
curl http://localhost:8082/ws/hotel.wsdl
```

### Maven non trouvé
```bash
sudo apt install maven
```

## 📋 Commandes Utiles

### Vérifier les ports utilisés
```bash
# Hotellerie (doit être sur 8082)
netstat -tuln | grep 8082

# Agence (doit être sur 8081)
netstat -tuln | grep 8081
```

### Nettoyer les builds
```bash
mvn clean
```

### Compiler sans tests
```bash
mvn clean compile -DskipTests
```

## 🎓 Workflow Complet

```
┌─────────────────────────────────────────────────┐
│ 1. Démarrer Hotellerie (Port 8082)             │
│    └─ Service SOAP pour la gestion d'hôtels    │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 2. Démarrer Agence (Port 8081)                 │
│    └─ API REST pour clients                    │
│    └─ Client SOAP pour Hotellerie              │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 3. Démarrer Client CLI                         │
│    └─ Client REST pour Agence                  │
│    └─ Interface utilisateur en ligne de cmd    │
└─────────────────────────────────────────────────┘
```

## ✅ Vérification du Système

### Test complet
1. ✅ Hotellerie répond au WSDL
   ```bash
   curl http://localhost:8082/ws/hotel.wsdl
   ```

2. ✅ Agence répond au ping
   ```bash
   curl http://localhost:8081/api/agence/ping
   ```

3. ✅ Client se connecte à l'agence
   ```
   Connexion à l'agence... ✓ Connecté
   ```

Si les 3 tests passent, votre système est opérationnel ! 🎉

---

**Pour plus d'informations, consultez [README.md](README.md)**

