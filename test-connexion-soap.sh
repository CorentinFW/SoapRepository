#!/bin/bash

echo "═════════════════════════════════════════════════"
echo "  Test du Système SOAP - Connexion Complète"
echo "═════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# 1. Arrêter tous les services
echo -e "${YELLOW}[1/6]${NC} Arrêt des services existants..."
pkill -9 -f "spring-boot:run" 2>/dev/null
sleep 2
echo -e "${GREEN}✓${NC} Services arrêtés"

# 2. Recompiler l'Agence avec le nouveau client SOAP
echo -e "${YELLOW}[2/6]${NC} Compilation de l'Agence..."
cd "$BASE_DIR/Agence"
mvn clean generate-sources compile > /tmp/agence-compile.log 2>&1
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Agence compilée"
else
    echo -e "${RED}✗${NC} Erreur de compilation - voir /tmp/agence-compile.log"
    tail -20 /tmp/agence-compile.log
    exit 1
fi

# 3. Démarrer Hotellerie
echo -e "${YELLOW}[3/6]${NC} Démarrage de l'Hotellerie (Port 8082)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run > /tmp/hotellerie.log 2>&1 &
HOTEL_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie démarrée (PID: $HOTEL_PID)"

# Attendre que l'Hotellerie démarre
echo -n "   Attente du démarrage"
for i in {1..15}; do
    sleep 1
    echo -n "."
    if grep -q "Started HotellerieApplication" /tmp/hotellerie.log 2>/dev/null; then
        break
    fi
done
echo ""

if grep -q "Started HotellerieApplication" /tmp/hotellerie.log; then
    echo -e "${GREEN}✓${NC} Hotellerie prête"
else
    echo -e "${RED}✗${NC} Hotellerie n'a pas démarré correctement"
    tail -10 /tmp/hotellerie.log
    kill $HOTEL_PID 2>/dev/null
    exit 1
fi

# 4. Test SOAP direct de l'Hotellerie
echo -e "${YELLOW}[4/6]${NC} Test SOAP de l'Hotellerie..."
SOAP_TEST=$(curl -s -X POST http://localhost:8082/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                         xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:rechercherChambresRequest>
         <hot:dateArrive>2025-12-01</hot:dateArrive>
         <hot:dateDepart>2025-12-05</hot:dateDepart>
      </hot:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>')

if echo "$SOAP_TEST" | grep -q "Chambre"; then
    CHAMBRE_COUNT=$(echo "$SOAP_TEST" | grep -o "Chambre" | wc -l)
    echo -e "${GREEN}✓${NC} Hotellerie SOAP fonctionne - $CHAMBRE_COUNT chambre(s) trouvée(s)"
else
    echo -e "${RED}✗${NC} Erreur SOAP Hotellerie"
    echo "$SOAP_TEST"
fi

# 5. Démarrer Agence
echo -e "${YELLOW}[5/6]${NC} Démarrage de l'Agence (Port 8081)..."
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence.log 2>&1 &
AGENCE_PID=$!
echo -e "${GREEN}✓${NC} Agence démarrée (PID: $AGENCE_PID)"

# Attendre que l'Agence démarre
echo -n "   Attente du démarrage"
for i in {1..15}; do
    sleep 1
    echo -n "."
    if grep -q "Started AgenceApplication" /tmp/agence.log 2>/dev/null; then
        break
    fi
done
echo ""

if grep -q "Started AgenceApplication" /tmp/agence.log; then
    echo -e "${GREEN}✓${NC} Agence prête"
else
    echo -e "${RED}✗${NC} Agence n'a pas démarré correctement"
    tail -10 /tmp/agence.log
    kill $HOTEL_PID $AGENCE_PID 2>/dev/null
    exit 1
fi

# 6. Test SOAP Agence
echo -e "${YELLOW}[6/6]${NC} Test SOAP de l'Agence..."
AGENCE_TEST=$(curl -s -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                         xmlns:soap="http://tp1.org/agence/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:rechercherChambresRequest>
         <soap:dateArrive>2025-12-01</soap:dateArrive>
         <soap:dateDepart>2025-12-05</soap:dateDepart>
      </soap:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>')

echo ""
echo "═════════════════════════════════════════════════"
echo "  RÉSULTAT DU TEST"
echo "═════════════════════════════════════════════════"

if echo "$AGENCE_TEST" | grep -q "chambre"; then
    CHAMBRE_COUNT=$(echo "$AGENCE_TEST" | grep -o "<id>" | wc -l)
    echo -e "${GREEN}✓ SUCCÈS !${NC} L'Agence communique avec l'Hotellerie en SOAP"
    echo -e "   ${CHAMBRE_COUNT} chambre(s) trouvée(s) via l'Agence"
    echo ""
    echo "Réponse:"
    echo "$AGENCE_TEST" | grep -o "<id>[0-9]*</id>" | head -5
else
    echo -e "${RED}✗ ÉCHEC${NC} - L'Agence ne retourne pas de chambres"
    echo ""
    echo "Réponse de l'Agence:"
    echo "$AGENCE_TEST"
    echo ""
    echo "Logs Agence (dernières lignes):"
    tail -20 /tmp/agence.log
fi

echo ""
echo "═════════════════════════════════════════════════"
echo "  Services en cours:"
echo "  - Hotellerie: PID $HOTEL_PID (Port 8082)"
echo "  - Agence: PID $AGENCE_PID (Port 8081)"
echo ""
echo "  Logs:"
echo "  - tail -f /tmp/hotellerie.log"
echo "  - tail -f /tmp/agence.log"
echo ""
echo "  Pour lancer le Client:"
echo "  cd $BASE_DIR/Client && mvn spring-boot:run"
echo ""
echo "  Pour arrêter:"
echo "  kill $HOTEL_PID $AGENCE_PID"
echo "═════════════════════════════════════════════════"

