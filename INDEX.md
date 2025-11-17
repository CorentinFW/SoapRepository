# 📚 INDEX - Documentation Système Multi-Hôtels

## 🎯 Démarrage Rapide

**Vous venez d'arriver sur le projet ? Commencez ici :**

```bash
./premier-test.sh
```

Ce script lance automatiquement tout le système (3 hôtels + agence + client).

---

## 📖 Documentation Disponible

### Pour Commencer

| Document | Description | Quand l'utiliser |
|----------|-------------|------------------|
| **[IMPLEMENTATION_COMPLETE.md](IMPLEMENTATION_COMPLETE.md)** | ✅ État du projet | Pour voir ce qui est fait |
| **[README_MULTI_HOTELS.md](README_MULTI_HOTELS.md)** | Guide utilisateur | Pour utiliser le système |
| **[GUIDE_TEST.md](GUIDE_TEST.md)** | Scénarios de test | Pour tester le système |

### Pour Comprendre

| Document | Description | Quand l'utiliser |
|----------|-------------|------------------|
| **[MULTI_HOTELS_CONFIG.md](MULTI_HOTELS_CONFIG.md)** | Configuration détaillée | Pour configurer les hôtels |
| **[CHANGELOG_TECHNIQUE.md](CHANGELOG_TECHNIQUE.md)** | Détails techniques | Pour comprendre le code |
| **[PROJET_COMPLET.md](PROJET_COMPLET.md)** | Architecture générale | Pour vue d'ensemble |

### Documentation Originale

| Document | Description |
|----------|-------------|
| **[QUICKSTART_SOAP.md](QUICKSTART_SOAP.md)** | Guide de démarrage rapide SOAP |
| **[LISEZ-MOI-SOAP.md](LISEZ-MOI-SOAP.md)** | Readme SOAP original |
| **[TRANSFORMATION_SOAP.md](TRANSFORMATION_SOAP.md)** | Transformation vers SOAP |

---

## 🚀 Scripts Disponibles

### Scripts Principaux

```bash
# Lancer tout le système (3 hôtels + agence + client)
./start-system-soap.sh

# Premier test guidé
./premier-test.sh

# Test rapide (Paris + agence seulement)
./test-rapide.sh

# Vérifier que les services sont démarrés
./test-3-hotels.sh
```

### Scripts par Module

```bash
# Démarrer l'hôtellerie Paris
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris

# Démarrer l'hôtellerie Lyon
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=lyon

# Démarrer l'hôtellerie Montpellier
cd Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier

# Démarrer l'agence
cd Agence
mvn spring-boot:run

# Démarrer le client
cd Client
mvn spring-boot:run
```

---

## 🏗️ Structure du Projet

```
SoapRepository/
│
├── 📄 Documentation
│   ├── IMPLEMENTATION_COMPLETE.md    ⭐ Résumé complet
│   ├── README_MULTI_HOTELS.md        ⭐ Guide utilisateur
│   ├── GUIDE_TEST.md                 ⭐ Tests
│   ├── MULTI_HOTELS_CONFIG.md        Configuration
│   ├── CHANGELOG_TECHNIQUE.md        Changements
│   ├── INDEX.md                      Ce fichier
│   └── ... (autres docs)
│
├── 🚀 Scripts
│   ├── premier-test.sh               ⭐ Premier test
│   ├── start-system-soap.sh          Démarrage complet
│   ├── test-rapide.sh                Test rapide
│   └── test-3-hotels.sh              Vérification
│
├── 🏨 Hotellerie/                    Service hôtel
│   ├── src/main/
│   │   ├── java/                     Code source
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-paris.properties
│   │       ├── application-lyon.properties
│   │       └── application-montpellier.properties
│   └── pom.xml
│
├── 🏢 Agence/                        Service agence
│   ├── src/main/
│   │   ├── java/
│   │   │   └── client/
│   │   │       └── MultiHotelSoapClient.java  ⭐ Nouveau
│   │   └── resources/
│   │       └── application.properties
│   └── pom.xml
│
└── 💻 Client/                        Client CLI
    ├── src/main/
    └── pom.xml
```

---

## 🎯 Parcours Recommandés

### Parcours 1: Utilisateur Final

1. ✅ Lire `IMPLEMENTATION_COMPLETE.md` (5 min)
2. ✅ Lancer `./premier-test.sh`
3. ✅ Utiliser le client CLI
4. ✅ Consulter `GUIDE_TEST.md` pour plus de scénarios

### Parcours 2: Testeur

1. ✅ Lire `GUIDE_TEST.md`
2. ✅ Lancer `./start-system-soap.sh`
3. ✅ Exécuter les tests manuels
4. ✅ Consulter les logs

### Parcours 3: Développeur

1. ✅ Lire `CHANGELOG_TECHNIQUE.md`
2. ✅ Examiner `MultiHotelSoapClient.java`
3. ✅ Consulter `MULTI_HOTELS_CONFIG.md`
4. ✅ Modifier et tester

### Parcours 4: Administrateur

1. ✅ Lire `MULTI_HOTELS_CONFIG.md`
2. ✅ Comprendre les profils Spring
3. ✅ Tester `./test-3-hotels.sh`
4. ✅ Consulter les logs

---

## 🔍 Recherche Rapide

### Je veux...

| Je veux... | Aller à... |
|-----------|-----------|
| **Démarrer le système** | `./premier-test.sh` |
| **Comprendre l'architecture** | `MULTI_HOTELS_CONFIG.md` |
| **Tester les fonctionnalités** | `GUIDE_TEST.md` |
| **Voir les changements** | `CHANGELOG_TECHNIQUE.md` |
| **Résoudre un problème** | `GUIDE_TEST.md` → Dépannage |
| **Configurer un nouvel hôtel** | `MULTI_HOTELS_CONFIG.md` |
| **Comprendre le code** | `CHANGELOG_TECHNIQUE.md` |

---

## 📊 Vue d'Ensemble du Système

### Services

| Service | Port | Type | Description |
|---------|------|------|-------------|
| Hotellerie Paris | 8082 | SOAP | Hôtel 5⭐ - 5 chambres |
| Hotellerie Lyon | 8083 | SOAP | Hôtel 4⭐ - 5 chambres |
| Hotellerie Montpellier | 8084 | SOAP | Hôtel 3⭐ - 5 chambres |
| Agence | 8081 | SOAP | Agrégateur - interroge les 3 hôtels |
| Client | - | CLI | Interface utilisateur |

### Flux de Données

```
1. Client CLI
      ↓ (commande utilisateur)
2. Agence
      ↓ (3 appels SOAP parallèles)
3. Paris + Lyon + Montpellier
      ↓ (réponses SOAP)
4. Agence (agrégation)
      ↓ (résultat final)
5. Client CLI (affichage)
```

---

## 🧪 Tests Disponibles

### Tests Automatiques

```bash
./test-3-hotels.sh        # Vérifie les WSDL
```

### Tests Manuels

Voir `GUIDE_TEST.md` pour :
- Test 1: Recherche multi-hôtels
- Test 2: Recherche ciblée par ville
- Test 3: Filtrage par prix
- Test 4: Réservation

---

## 📝 Logs

```bash
# Tous les logs sont dans /tmp/

tail -f /tmp/hotellerie-paris.log
tail -f /tmp/hotellerie-lyon.log
tail -f /tmp/hotellerie-montpellier.log
tail -f /tmp/agence.log
```

---

## 🎓 Technologies

- **Architecture:** Microservices SOAP
- **Framework:** Spring Boot
- **Web Services:** Spring-WS (SOAP)
- **Marshalling:** JAXB
- **Build:** Maven
- **Profils:** Spring Profiles

---

## 📞 Support

### En cas de problème

1. **Consulter** `GUIDE_TEST.md` section Dépannage
2. **Vérifier** les logs dans `/tmp/`
3. **Lire** `CHANGELOG_TECHNIQUE.md` pour comprendre

### Erreurs Communes

| Erreur | Solution |
|--------|----------|
| Port déjà utilisé | `netstat -tuln \| grep 8082` puis `kill -9 <PID>` |
| Service ne démarre pas | `tail -f /tmp/hotellerie-paris.log` |
| Aucune chambre trouvée | Vérifier critères, voir logs agence |
| Compilation échoue | Vérifier encodage fichiers properties |

---

## ✅ Checklist de Validation

Avant de dire "ça marche" :

- [ ] Compilation réussie (Hotellerie et Agence)
- [ ] Les 3 hôtelleries démarrent
- [ ] L'agence démarre
- [ ] Le client démarre
- [ ] Recherche retourne des chambres
- [ ] On voit des chambres des 3 hôtels
- [ ] Une réservation fonctionne
- [ ] Les logs sont propres

---

## 🚀 Pour Aller Plus Loin

### Améliorations Possibles

1. **Base de données** - Remplacer les données en mémoire
2. **Tests automatisés** - JUnit + Mockito
3. **Parallélisation** - CompletableFuture pour les appels SOAP
4. **Cache** - Redis pour les recherches
5. **Monitoring** - Prometheus/Grafana
6. **Interface web** - Front-end React/Angular

### Ajouter un Nouvel Hôtel

1. Créer `application-nice.properties`
2. Définir port (ex: 8085) et catégorie
3. Ajouter dans `application.properties` de l'Agence
4. Modifier `start-system-soap.sh`
5. Tester !

Voir `MULTI_HOTELS_CONFIG.md` pour détails.

---

## 📈 Métriques

- **Modules:** 3 (Hotellerie, Agence, Client)
- **Services:** 4 (3 hôtels + 1 agence)
- **Ports:** 8081-8084
- **Chambres:** 15 (5 par hôtel)
- **Prix:** 45€ - 200€
- **Villes:** 3 (Paris, Lyon, Montpellier)

---

## 🎯 Points Clés à Retenir

1. ✅ **SOAP pur** - Pas de REST
2. ✅ **Spring Boot** - Framework principal
3. ✅ **3 hôtels indépendants** - Architecture distribuée
4. ✅ **Agence agrégateur** - Point central
5. ✅ **Client CLI** - Interface simple
6. ✅ **Données en mémoire** - Réinitialisation à chaque démarrage
7. ✅ **Profils Spring** - Configuration flexible

---

## 🎁 Bonus

### Commande One-Liner

```bash
# Tout en une commande
cd /home/corentinfay/Bureau/SoapRepository && ./premier-test.sh
```

### Alias Utiles

Ajoutez dans votre `~/.bashrc` :

```bash
alias hotel-start='cd ~/Bureau/SoapRepository && ./start-system-soap.sh'
alias hotel-test='cd ~/Bureau/SoapRepository && ./test-3-hotels.sh'
alias hotel-logs='tail -f /tmp/agence.log'
```

---

## 📅 Dernière Mise à Jour

**Date:** 2025-11-17  
**Version:** 1.0 - Multi-Hôtels  
**Statut:** ✅ Prêt pour tests

---

**Bonne exploration du système ! 🎉**

