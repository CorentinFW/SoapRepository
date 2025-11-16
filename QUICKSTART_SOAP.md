# 🚀 Démarrage Rapide - Système SOAP

## Prérequis

✅ Java 8 (JDK installé)  
✅ Maven  
✅ Ports 8081 et 8082 disponibles

## Démarrage en 1 commande

```bash
cd /home/corentinfay/Bureau/SoapRepository
./start-system-soap.sh
```

## Ou démarrage manuel (3 terminaux)

### Terminal 1 : Hotellerie
```bash
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```
Attendez le message : `Started HotellerieApplication`

### Terminal 2 : Agence
```bash
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn spring-boot:run
```
Attendez le message : `Started AgenceApplication`

### Terminal 3 : Client
```bash
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

## Test rapide

Une fois le client démarré, vous verrez :

```
╔═══════════════════════════════════════════════════╗
║   SYSTÈME DE RÉSERVATION HÔTELIÈRE - CLIENT SOAP  ║
╚═══════════════════════════════════════════════════╝

Connexion à l'agence SOAP... ✓ Connecté
```

### Scénario de test

1. **Choisir option 1** : Rechercher des chambres
   - Adresse : `Paris` (ou laisser vide)
   - Date arrivée : `2025-12-01`
   - Date départ : `2025-12-05`
   - Prix max : `200` (autres champs : Enter)

2. **Résultat** : Liste des chambres disponibles avec ID, prix, nombre de lits

3. **Choisir option 2** : Effectuer une réservation
   - ID chambre : `2` (un ID de la liste)
   - Nom : `Dupont`
   - Prénom : `Jean`
   - Carte bleue : `1234567890123456`
   - Dates : comme la recherche

4. **Résultat** : Confirmation avec ID de réservation

## Vérification que SOAP fonctionne

```bash
# Vérifier le WSDL de l'Agence
curl http://localhost:8081/ws/agence.wsdl | head -20

# Vérifier le WSDL de l'Hotellerie
curl http://localhost:8082/ws/hotel.wsdl | head -20

# Test SOAP direct (Ping)
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:pingRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Arrêt

- Dans le terminal du Client : Choisir option **4** (Quitter)
- Puis : `Ctrl+C` dans les terminaux Agence et Hotellerie

Ou si vous avez utilisé le script :
```bash
pkill -f "spring-boot:run"
```

## Dépannage

### Port déjà utilisé
```bash
# Trouver et tuer les processus
lsof -ti:8081 | xargs kill -9
lsof -ti:8082 | xargs kill -9
```

### Logs
```bash
# Hotellerie
tail -f /tmp/hotellerie.log

# Agence
tail -f /tmp/agence.log
```

### Recompiler
```bash
cd Agence && mvn clean compile
cd ../Client && mvn clean compile
cd ../Hotellerie && mvn clean compile
```

## 📚 Documentation complète

- `README_SOAP.md` - Documentation détaillée
- `TRANSFORMATION_SOAP.md` - Détails de la transformation REST→SOAP

