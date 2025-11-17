# 🏨 Système de Réservation Multi-Hôtels - SOAP

## 🎯 Vue d'ensemble

Système de réservation hôtelière avec **3 hôtels indépendants** communiquant via **SOAP**.

- 🏨 **Paris** (5⭐) - Port 8082
- 🏨 **Lyon** (4⭐) - Port 8083  
- 🏨 **Montpellier** (3⭐) - Port 8084
- 🏢 **Agence** - Port 8081 (agrège les 3 hôtels)

**15 chambres** au total (5 par hôtel) avec données en mémoire.

---

## 🚀 Démarrage Rapide

### Prérequis
- Java 8+
- Maven 3.6+

### Lancer le Système Complet

```bash
./start-system-soap.sh
```

Cela démarre automatiquement :
1. Les 3 hôtelleries
2. L'agence
3. Le client CLI

**Temps de démarrage**: ~60 secondes

### Test Rapide (1 hôtel seulement)

```bash
./test-rapide.sh
```

Lance seulement Paris + Agence pour tester rapidement.

---

## 📖 Documentation

- **[MULTI_HOTELS_CONFIG.md](MULTI_HOTELS_CONFIG.md)** - Configuration détaillée des 3 hôtels
- **[GUIDE_TEST.md](GUIDE_TEST.md)** - Guide de test complet avec scénarios
- **[PROJET_COMPLET.md](PROJET_COMPLET.md)** - Architecture générale du projet

---

## 🧪 Vérification

### Vérifier que les services sont démarrés

```bash
./test-3-hotels.sh
```

### Tester manuellement

```bash
# Paris
curl http://localhost:8082/ws?wsdl

# Lyon  
curl http://localhost:8083/ws?wsdl

# Montpellier
curl http://localhost:8084/ws?wsdl

# Agence
curl http://localhost:8081/ws?wsdl
```

---

## 📊 Architecture

```
Client CLI (Spring Boot)
    ↓ SOAP
Agence (Spring WS) - Port 8081
    ↓ SOAP
    ├→ Hotellerie Paris (Spring WS) - Port 8082
    ├→ Hotellerie Lyon (Spring WS) - Port 8083
    └→ Hotellerie Montpellier (Spring WS) - Port 8084
```

**Technologies:**
- ✅ SOAP (Spring Web Services)
- ✅ JAXB pour marshalling/unmarshalling
- ✅ Spring Boot
- ✅ Maven

**Pas de REST** - 100% SOAP

---

## 🎮 Utilisation du Client CLI

Une fois le système démarré, le client CLI s'affiche avec un menu :

```
1. Rechercher des chambres
2. Effectuer une réservation
3. Quitter
```

### Exemple de Recherche

**Recherche dans tous les hôtels:**
- Adresse: (laisser vide)
- Prix min: 50
- Prix max: 150
- Nombre d'étoiles: (laisser vide)
- Nombre de lits: 2

**Résultat:** Chambres de tous les hôtels entre 50€ et 150€ avec 2 lits

---

## 📝 Logs

Les logs sont dans `/tmp/`:

```bash
tail -f /tmp/hotellerie-paris.log
tail -f /tmp/hotellerie-lyon.log
tail -f /tmp/hotellerie-montpellier.log
tail -f /tmp/agence.log
```

---

## 🛠️ Dépannage

### Port déjà utilisé

```bash
# Trouver et tuer le processus
netstat -tuln | grep 8082
kill -9 <PID>
```

### Service ne démarre pas

```bash
# Vérifier les logs
tail -f /tmp/hotellerie-paris.log
```

### Aucune chambre trouvée

- Vérifier que les 3 hôtels sont démarrés
- Élargir les critères de recherche
- Consulter les logs de l'agence

---

## 🏗️ Structure du Projet

```
SoapRepository/
├── Hotellerie/          # Service hôtel (3 instances)
│   └── src/main/resources/
│       ├── application-paris.properties
│       ├── application-lyon.properties
│       └── application-montpellier.properties
├── Agence/              # Service agence
│   └── src/main/java/
│       └── client/
│           └── MultiHotelSoapClient.java
├── Client/              # Client CLI
├── start-system-soap.sh # Script de démarrage
├── test-3-hotels.sh     # Script de test
└── test-rapide.sh       # Test rapide
```

---

## 👨‍💻 Développement

### Compiler un module

```bash
cd Hotellerie
mvn clean compile

cd ../Agence
mvn clean compile

cd ../Client
mvn clean compile
```

### Lancer manuellement

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

---

## ✨ Fonctionnalités

- ✅ Recherche de chambres dans les 3 hôtels simultanément
- ✅ Filtrage par adresse, prix, étoiles, nombre de lits
- ✅ Réservation de chambres
- ✅ Vérification de disponibilité par dates
- ✅ Gestion des clients

---

## 📈 Prochaines Étapes

1. Ajouter une base de données
2. Implémenter un cache
3. Ajouter plus d'hôtels
4. Monitoring et métriques
5. Tests automatisés

---

## 📄 Licence

Projet académique - TP Systèmes Distribués

---

**Bon test ! 🚀**

