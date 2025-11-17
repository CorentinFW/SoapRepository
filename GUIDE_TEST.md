# 🎯 Guide de Test - Système Multi-Hôtels

## ✅ Modifications Complétées

Le système a été configuré avec succès pour gérer **3 hôtelleries indépendantes** :

### Nouveaux Fichiers Créés

1. **Configurations des Hôtels** (Hotellerie/src/main/resources/):
   - ✅ `application-paris.properties` (Port 8082, 5⭐)
   - ✅ `application-lyon.properties` (Port 8083, 4⭐)
   - ✅ `application-montpellier.properties` (Port 8084, 3⭐)

2. **Client Multi-Hôtels** (Agence/):
   - ✅ `MultiHotelSoapClient.java` - Client SOAP qui interroge les 3 hôtels

3. **Scripts de Test**:
   - ✅ `start-system-soap.sh` - Lance les 3 hôtels + agence + client
   - ✅ `test-3-hotels.sh` - Vérifie que les 3 hôtels sont accessibles
   - ✅ `test-rapide.sh` - Lance seulement Paris + Agence pour test rapide

### Fichiers Modifiés

1. ✅ **HotelService.java** - Utilise les propriétés de configuration pour initialiser différents hôtels
2. ✅ **AgenceService.java** - Utilise MultiHotelSoapClient pour interroger plusieurs hôtels
3. ✅ **application.properties (Agence)** - Configure les URLs des 3 hôtels

---

## 🚀 Comment Tester

### Option 1: Test Rapide (1 hôtel)

```bash
./test-rapide.sh
```

Ce script lance :
- ✅ Hotellerie Paris (Port 8082)
- ✅ Agence (Port 8081)

**Temps d'attente**: ~30 secondes

### Option 2: Système Complet (3 hôtels)

```bash
./start-system-soap.sh
```

Ce script lance :
- ✅ Hotellerie Paris (Port 8082)
- ✅ Hotellerie Lyon (Port 8083)
- ✅ Hotellerie Montpellier (Port 8084)
- ✅ Agence (Port 8081)
- ✅ Client CLI

**Temps d'attente**: ~60 secondes

### Option 3: Vérification des Services

```bash
./test-3-hotels.sh
```

Ce script vérifie que tous les services SOAP sont accessibles.

---

## 🧪 Tests Manuels

### 1. Vérifier qu'un Hôtel est Démarré

```bash
# Paris
curl http://localhost:8082/ws?wsdl

# Lyon
curl http://localhost:8083/ws?wsdl

# Montpellier
curl http://localhost:8084/ws?wsdl
```

### 2. Vérifier l'Agence

```bash
curl http://localhost:8081/ws?wsdl
```

### 3. Consulter les Logs

```bash
# Hotellerie Paris
tail -f /tmp/hotellerie-paris.log

# Hotellerie Lyon
tail -f /tmp/hotellerie-lyon.log

# Hotellerie Montpellier
tail -f /tmp/hotellerie-montpellier.log

# Agence
tail -f /tmp/agence.log
```

---

## 🔍 Test des Fonctionnalités

### Test 1: Recherche dans UN Hôtel (Paris)

**Critères:**
- Adresse: Paris
- Prix: 50€ - 150€
- Étoiles: 5
- Lits: 2

**Résultat Attendu:** 
- Chambre Double (120€, 2 lits) de Paris
- Chambre Familiale (150€, 4 lits) de Paris

### Test 2: Recherche dans PLUSIEURS Hôtels

**Critères:**
- Adresse: (vide ou "France")
- Prix: 40€ - 100€
- Étoiles: (non spécifié)
- Lits: 1

**Résultat Attendu:** 
Chambres de **tous les hôtels** correspondant aux critères :
- Paris: Chambre Economy (60€), Chambre Simple (80€)
- Lyon: Chambre Budget (50€), Chambre Standard (70€)
- Montpellier: Chambre Eco (45€), Studio (65€)

**Total:** ~6 chambres

### Test 3: Recherche par Ville

**Critères:**
- Adresse: Lyon
- Prix: (non spécifié)
- Étoiles: 4
- Lits: (non spécifié)

**Résultat Attendu:** 
Toutes les chambres de l'hôtel Lyon (5 chambres)

---

## 🏗️ Architecture Technique

```
Client CLI
    ↓ SOAP
Agence (MultiHotelSoapClient)
    ↓ SOAP (parallel)
    ├→ Hotellerie Paris    (HotelService → 5 chambres)
    ├→ Hotellerie Lyon     (HotelService → 5 chambres)
    └→ Hotellerie Montpellier (HotelService → 5 chambres)
```

### Données Initialisées

**Paris (5⭐):**
- ID 1: Chambre Simple (80€, 1 lit)
- ID 2: Chambre Double (120€, 2 lits)
- ID 3: Suite Deluxe (200€, 3 lits)
- ID 4: Chambre Familiale (150€, 4 lits)
- ID 5: Chambre Economy (60€, 1 lit)

**Lyon (4⭐):**
- ID 11: Chambre Standard (70€, 1 lit)
- ID 12: Chambre Confort (100€, 2 lits)
- ID 13: Suite Junior (150€, 2 lits)
- ID 14: Chambre Triple (130€, 3 lits)
- ID 15: Chambre Budget (50€, 1 lit)

**Montpellier (3⭐):**
- ID 21: Chambre Eco (45€, 1 lit)
- ID 22: Chambre Double Confort (85€, 2 lits)
- ID 23: Suite Vue Mer (140€, 2 lits)
- ID 24: Chambre Quad (110€, 4 lits)
- ID 25: Studio (65€, 1 lit)

**Total:** 15 chambres réparties sur 3 hôtels

---

## 🐛 Dépannage

### Problème: "Failed to execute goal... Input length = 1"

**Cause:** Problème d'encodage dans les fichiers .properties
**Solution:** ✅ Déjà corrigé - caractères accentués remplacés

### Problème: Port déjà utilisé

```bash
# Trouver le processus qui utilise le port
netstat -tuln | grep 8082

# Tuer le processus
kill -9 <PID>
```

### Problème: Aucune chambre trouvée

**Vérifications:**
1. Les hôtels sont-ils démarrés ? → `curl http://localhost:8082/ws?wsdl`
2. L'agence est-elle démarrée ? → `curl http://localhost:8081/ws?wsdl`
3. Les critères sont-ils trop restrictifs ?
4. Consulter les logs : `tail -f /tmp/agence.log`

### Problème: Service ne démarre pas

```bash
# Voir les logs détaillés
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris

# Ou consulter le log
tail -f /tmp/hotellerie-paris.log
```

---

## 📊 Scénarios de Test Recommandés

### Scénario 1: Test Unitaire (1 hôtel)
```bash
./test-rapide.sh
# Attendre 30s
curl http://localhost:8082/ws?wsdl | grep -i "wsdl"
# Devrait retourner du XML WSDL
```

### Scénario 2: Test d'Intégration (3 hôtels)
```bash
./start-system-soap.sh
# Le client CLI va démarrer automatiquement
# Faire une recherche avec critères larges pour voir les 3 hôtels
```

### Scénario 3: Test de Charge (Simulation)
```bash
# En parallèle, lancer plusieurs requêtes
for i in {1..10}; do
  curl -s "http://localhost:8081/ws?wsdl" > /dev/null &
done
wait
echo "Test terminé"
```

---

## ✨ Prochaines Étapes

1. **Tester le Client CLI** - Vérifier que toutes les commandes fonctionnent
2. **Tester les Réservations** - Vérifier qu'on peut réserver sur les 3 hôtels
3. **Ajouter des Tests Unitaires** - Créer des tests automatisés
4. **Documentation** - Compléter la documentation utilisateur

---

## 📝 Notes Importantes

- ✅ Tous les services utilisent **SOAP pur** (Spring Web Services)
- ✅ Aucun REST dans le système
- ✅ Chaque hôtel est **indépendant** avec ses propres données
- ✅ L'agence **interroge tous les hôtels en parallèle**
- ✅ Les données sont **en mémoire** (réinitialisées à chaque démarrage)
- ✅ Les IDs de chambre sont **uniques** par hôtel (1-5, 11-15, 21-25)

---

## 🎯 Validation Finale

Pour valider que tout fonctionne :

```bash
# 1. Lancer le système complet
./start-system-soap.sh

# 2. Dans un autre terminal, vérifier les services
./test-3-hotels.sh

# 3. Le client CLI devrait démarrer automatiquement
# 4. Faire une recherche de chambres
# 5. Vérifier qu'on obtient des résultats des 3 hôtels
```

**Indicateurs de Succès:**
- ✅ 3 hôtelleries démarrées sur ports 8082, 8083, 8084
- ✅ 1 agence démarrée sur port 8081
- ✅ Client CLI fonctionnel
- ✅ Recherche retourne des chambres de plusieurs hôtels
- ✅ Réservations fonctionnelles

---

Tout est prêt ! 🚀

