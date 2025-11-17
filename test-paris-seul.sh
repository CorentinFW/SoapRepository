#!/bin/bash

# Script de test rapide - Lance seulement Paris pour tester

echo "═══════════════════════════════════════════════════════════"
echo "   Test Rapide - Hotellerie Paris Seule"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# Démarrer Hotellerie Paris
echo -e "${YELLOW}Démarrage Hotellerie Paris...${NC}"
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=paris &
PARIS_PID=$!

# Attendre 15 secondes
echo "Attente démarrage (15s)..."
sleep 15

# Tester
echo ""
echo -e "${YELLOW}Test du WSDL...${NC}"
if curl -s "http://localhost:8082/ws?wsdl" | head -20; then
    echo ""
    echo -e "${GREEN}✓ Service accessible!${NC}"
else
    echo -e "${RED}✗ Service non accessible${NC}"
fi

echo ""
echo -e "${YELLOW}Arrêt du service...${NC}"
kill $PARIS_PID 2>/dev/null
echo -e "${GREEN}✓ Terminé${NC}"

