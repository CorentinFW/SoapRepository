# Guide de Démarrage Rapide - Agence

## Installation et Démarrage

### 1. Compilation
```bash
cd /home/etudiant/Bureau/SoapRepository/Agence
mvn clean install
```

### 2. Démarrage
```bash
mvn spring-boot:run
```

L'application démarre sur **http://localhost:8081**

### 3. Vérification
Dans un autre terminal :
```bash
curl http://localhost:8081/api/agence/ping
```

Vous devriez voir : `Agence SOAP opérationnelle`

## Tests Rapides

### Utiliser le script de test
```bash
# Démarrer l'Agence dans un terminal
mvn spring-boot:run

# Dans un autre terminal, lancer les tests
./test-agence.sh
```

### Tests manuels

**Test 1 : Ping**
```bash
curl http://localhost:8081/api/agence/ping
```

**Test 2 : Recherche**
```bash
curl -X POST http://localhost:8081/api/agence/rechercher \
  -H "Content-Type: application/json" \
  -d '{
    "adresse": "Paris",
    "dateArrive": "2025-12-01",
    "dateDepart": "2025-12-05",
    "prixMin": 0,
    "prixMax": 200,
    "nbrEtoile": 4,
    "nbrLits": 2
  }'
```

**Test 3 : Réservation**
```bash
curl -X POST http://localhost:8081/api/agence/reserver \
  -H "Content-Type: application/json" \
  -d '{
    "clientNom": "Dupont",
    "clientPrenom": "Jean",
    "clientNumeroCarteBleue": "1234567890123456",
    "chambreId": 1,
    "hotelAdresse": "Rue de la Paix, Paris",
    "dateArrive": "2025-12-01",
    "dateDepart": "2025-12-05"
  }'
```

## Arrêt

Dans le terminal où l'Agence tourne :
```
Ctrl + C
```

## Problèmes Courants

### Port 8081 déjà utilisé
```bash
# Trouver et tuer le processus
lsof -i :8081
kill -9 <PID>

# OU changer le port dans application.properties
server.port=8090
```

### Erreur de compilation
```bash
# Nettoyer et recompiler
mvn clean
mvn install
```

### L'application ne démarre pas
Vérifier Java :
```bash
java -version  # Doit être Java 8 ou supérieur
echo $JAVA_HOME
```

## Prochaines Étapes

1. **Transformer Hotellerie en service SOAP** (prioritaire)
2. Connecter l'Agence aux hôtels SOAP
3. Créer le client REST

Voir `ARCHITECTURE.md` pour plus de détails.

