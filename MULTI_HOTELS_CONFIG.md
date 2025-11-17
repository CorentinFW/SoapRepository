# Système Multi-Hôtels - Configuration 3 Villes

## Vue d'ensemble

Le système a été configuré pour gérer **3 hôtelleries indépendantes** :
- 🏨 **Paris** - Port 8082 (5 étoiles)
- 🏨 **Lyon** - Port 8083 (4 étoiles)  
- 🏨 **Montpellier** - Port 8084 (3 étoiles)

Chaque hôtellerie fonctionne comme un **serveur SOAP indépendant** avec ses propres chambres et tarifs.

## Architecture

```
┌─────────────────┐
│     Client      │
│   (CLI SOAP)    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│     Agence      │◄─── Port 8081
│  (SOAP Server)  │
└────────┬────────┘
         │
         ├──────────────┬──────────────┐
         ▼              ▼              ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ Hotellerie   │ │ Hotellerie   │ │ Hotellerie   │
│   PARIS      │ │    LYON      │ │ MONTPELLIER  │
│  Port 8082   │ │  Port 8083   │ │  Port 8084   │
└──────────────┘ └──────────────┘ └──────────────┘
```

## Configuration des Hôtels

### 🏨 Paris (5★)
- **Port**: 8082
- **Nom**: Grand Hotel Paris
- **Adresse**: 10 Rue de la Paix, Paris
- **Chambres**:
  - ID 1: Chambre Simple (80€, 1 lit)
  - ID 2: Chambre Double (120€, 2 lits)
  - ID 3: Suite Deluxe (200€, 3 lits)
  - ID 4: Chambre Familiale (150€, 4 lits)
  - ID 5: Chambre Economy (60€, 1 lit)

### 🏨 Lyon (4★)
- **Port**: 8083
- **Nom**: Hotel Lyon Centre
- **Adresse**: 25 Place Bellecour, Lyon
- **Chambres**:
  - ID 11: Chambre Standard (70€, 1 lit)
  - ID 12: Chambre Confort (100€, 2 lits)
  - ID 13: Suite Junior (150€, 2 lits)
  - ID 14: Chambre Triple (130€, 3 lits)
  - ID 15: Chambre Budget (50€, 1 lit)

### 🏨 Montpellier (3★)
- **Port**: 8084
- **Nom**: Hotel Mediterranee
- **Adresse**: 15 Rue de la Loge, Montpellier
- **Chambres**:
  - ID 21: Chambre Eco (45€, 1 lit)
  - ID 22: Chambre Double Confort (85€, 2 lits)
  - ID 23: Suite Vue Mer (140€, 2 lits)
  - ID 24: Chambre Quad (110€, 4 lits)
  - ID 25: Studio (65€, 1 lit)

## Fichiers de Configuration

### Hotellerie - Profils Spring

Trois profils ont été créés pour différencier les hôtels :

- `application-paris.properties` → Port 8082
- `application-lyon.properties` → Port 8083
- `application-montpellier.properties` → Port 8084

### Agence - Configuration Multi-Hôtels

Fichier: `Agence/src/main/resources/application.properties`
```properties
hotel.soap.urls=http://localhost:8082/ws,http://localhost:8083/ws,http://localhost:8084/ws
```

## Démarrage du Système

### Script Principal
```bash
./start-system-soap.sh
```

Ce script :
1. ✅ Démarre l'hôtellerie Paris (port 8082)
2. ✅ Démarre l'hôtellerie Lyon (port 8083)
3. ✅ Démarre l'hôtellerie Montpellier (port 8084)
4. ✅ Démarre l'agence (port 8081)
5. ✅ Démarre le client CLI

### Démarrage Manuel

Pour démarrer chaque service individuellement :

**Hotellerie Paris:**
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris
```

**Hotellerie Lyon:**
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=lyon
```

**Hotellerie Montpellier:**
```bash
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier
```

**Agence:**
```bash
cd Agence
mvn spring-boot:run
```

**Client:**
```bash
cd Client
mvn spring-boot:run
```

## Test du Système

### Script de Test Automatique
```bash
./test-3-hotels.sh
```

Ce script vérifie que les 3 hôtelleries et l'agence sont accessibles.

### Test Manuel avec curl

**Paris:**
```bash
curl http://localhost:8082/ws?wsdl
```

**Lyon:**
```bash
curl http://localhost:8083/ws?wsdl
```

**Montpellier:**
```bash
curl http://localhost:8084/ws?wsdl
```

**Agence:**
```bash
curl http://localhost:8081/ws?wsdl
```

## Logs

Les logs de chaque service sont disponibles dans `/tmp/` :
```bash
tail -f /tmp/hotellerie-paris.log
tail -f /tmp/hotellerie-lyon.log
tail -f /tmp/hotellerie-montpellier.log
tail -f /tmp/agence.log
```

## Fonctionnement

### Recherche de Chambres

Quand un client effectue une recherche via l'agence :
1. L'agence reçoit la requête SOAP
2. Elle interroge **les 3 hôtelleries en parallèle**
3. Elle agrège les résultats
4. Elle retourne la liste complète au client

**Exemple de recherche :**
- Critères : Lyon, 2-4 étoiles, 50€-150€, 2 lits
- Résultat : L'agence interroge les 3 hôtels, mais seul Lyon correspondra aux critères d'adresse

### Réservation

Pour une réservation :
1. Le client envoie l'ID de chambre à l'agence
2. L'agence essaie la réservation sur chaque hôtel
3. L'hôtel qui possède cette chambre confirme la réservation

## Modifications Apportées

### Nouveaux Fichiers

1. **Configurations Hotellerie:**
   - `Hotellerie/src/main/resources/application-paris.properties`
   - `Hotellerie/src/main/resources/application-lyon.properties`
   - `Hotellerie/src/main/resources/application-montpellier.properties`

2. **Client Multi-Hôtels:**
   - `Agence/src/main/java/org/tp1/agence/client/MultiHotelSoapClient.java`

3. **Scripts:**
   - `test-3-hotels.sh`

### Fichiers Modifiés

1. **HotelService.java:**
   - Ajout de `@Value` pour lire les propriétés de configuration
   - Initialisation dynamique selon le profil (Paris/Lyon/Montpellier)
   - Chambres différentes pour chaque ville

2. **AgenceService.java:**
   - Utilise maintenant `MultiHotelSoapClient` au lieu de `RealHotelSoapClient`

3. **application.properties (Agence):**
   - Configuration des URLs des 3 hôtels

4. **start-system-soap.sh:**
   - Lance 3 instances d'hôtellerie avec profils différents
   - Attend que chaque service soit prêt avant de continuer

## Avantages de cette Architecture

✅ **Scalabilité**: Facile d'ajouter de nouvelles hôtelleries
✅ **Isolation**: Chaque hôtel a ses propres données
✅ **Résilience**: Si un hôtel tombe, les autres continuent de fonctionner
✅ **Réalisme**: Simule un vrai système distribué

## Prochaines Étapes

Pour améliorer le système :
1. Ajouter une base de données pour la persistance
2. Implémenter un système de cache
3. Ajouter plus de villes
4. Implémenter un load balancer
5. Ajouter des métriques et monitoring

## Dépannage

### Problème: Un hôtel ne démarre pas
```bash
# Vérifier les logs
tail -f /tmp/hotellerie-paris.log

# Vérifier si le port est déjà utilisé
netstat -tuln | grep 8082
```

### Problème: L'agence ne trouve pas les hôtels
```bash
# Vérifier que les 3 hôtels sont démarrés
curl http://localhost:8082/ws?wsdl
curl http://localhost:8083/ws?wsdl
curl http://localhost:8084/ws?wsdl
```

### Problème: Pas de chambres trouvées
- Vérifier les critères de recherche (adresse, prix, étoiles, lits)
- Vérifier que les dates sont valides
- Consulter les logs de l'agence pour voir les appels SOAP

## Résumé Technique

- **Technologie**: SOAP (Spring Web Services)
- **Marshalling**: JAXB
- **Architecture**: Microservices distribués
- **Nombre de services**: 4 (3 hôtels + 1 agence)
- **Ports utilisés**: 8081-8084
- **Total chambres**: 15 (5 par hôtel)

