#!/bin/bash

# Script pour finaliser l'implémentation "Afficher Réservations par Hôtel"

echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║   Finalisation: Afficher Réservations par Hôtel             ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

echo -e "${BLUE}[1/5]${NC} Démarrage de l'Agence pour générer le WSDL..."
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence-temp.log 2>&1 &
AGENCE_PID=$!
echo "  PID: $AGENCE_PID"

echo -ne "${YELLOW}  Attente du démarrage...${NC}"
for i in {1..60}; do
    if curl -s --max-time 2 "http://localhost:8081/ws/agence.wsdl" > /dev/null 2>&1; then
        echo -e " ${GREEN}✓${NC}"
        break
    fi
    sleep 1
    echo -ne "."
done
echo ""

echo -e "${BLUE}[2/5]${NC} Téléchargement du WSDL mis à jour..."
curl -s "http://localhost:8081/ws/agence.wsdl" > "$BASE_DIR/Client/src/main/resources/wsdl/agence.wsdl"
if grep -q "getAllReservationsByHotel" "$BASE_DIR/Client/src/main/resources/wsdl/agence.wsdl"; then
    echo -e "${GREEN}  ✓ WSDL contient les nouveaux endpoints${NC}"
else
    echo -e "${RED}  ✗ WSDL ne contient pas getAllReservationsByHotel${NC}"
    kill $AGENCE_PID 2>/dev/null
    exit 1
fi

echo -e "${BLUE}[3/5]${NC} Arrêt de l'Agence temporaire..."
kill $AGENCE_PID 2>/dev/null
wait $AGENCE_PID 2>/dev/null
echo -e "${GREEN}  ✓ Agence arrêtée${NC}"

echo -e "${BLUE}[4/5]${NC} Recompilation du Client avec le nouveau WSDL..."
cd "$BASE_DIR/Client"
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo -e "${GREEN}  ✓ Client recompilé${NC}"
else
    echo -e "${RED}  ✗ Erreur de compilation${NC}"
    exit 1
fi

echo -e "${BLUE}[5/5]${NC} Vérification des classes générées..."
if [ -f "$BASE_DIR/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/GetAllReservationsByHotelRequest.java" ]; then
    echo -e "${GREEN}  ✓ GetAllReservationsByHotelRequest généré${NC}"
else
    echo -e "${RED}  ✗ Classes JAXB non générées${NC}"
    exit 1
fi

if [ -f "$BASE_DIR/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/GetAllReservationsByHotelResponse.java" ]; then
    echo -e "${GREEN}  ✓ GetAllReservationsByHotelResponse généré${NC}"
else
    echo -e "${RED}  ✗ Classes JAXB non générées${NC}"
    exit 1
fi

echo ""
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║              ✅ GÉNÉRATION JAXB TERMINÉE                     ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""
echo -e "${GREEN}Prochaines étapes:${NC}"
echo "  1. Ajouter la méthode dans AgenceSoapClient.java"
echo "  2. Modifier ClientCLISoap.java pour ajouter l'option au menu"
echo "  3. Tester avec ./start-robuste.sh"
echo ""
echo -e "${YELLOW}Voir IMPLEMENTATION_RESERVATIONS.md pour les détails${NC}"
echo ""

