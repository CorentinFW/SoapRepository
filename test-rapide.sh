#!/bin/bash

# Script de test rapide - Lance Paris et l'Agence pour test rapide

echo "═══════════════════════════════════════════════════════════"
echo "   Test Rapide - 1 Hotel + Agence"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# Fonction pour tuer les processus à la sortie
cleanup() {
    echo ""
    echo -e "${YELLOW}Arrêt des services...${NC}"
    kill $PARIS_PID $AGENCE_PID 2>/dev/null
    wait $PARIS_PID $AGENCE_PID 2>/dev/null
    echo -e "${GREEN}✓${NC} Services arrêtés"
    exit 0
}

trap cleanup EXIT INT TERM

# Démarrer Hotellerie Paris
echo -e "${BLUE}[1/2]${NC} Démarrage Hotellerie Paris (Port 8082)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=paris > /tmp/hotellerie-paris.log 2>&1 &
PARIS_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie Paris démarrée (PID: $PARIS_PID)"

# Attendre que le service soit prêt
echo -ne "${YELLOW}  Attente du service...${NC}"
for i in {1..30}; do
    if curl -s "http://localhost:8082/ws?wsdl" > /dev/null 2>&1; then
        echo -e " ${GREEN}✓${NC}"
        break
    fi
    sleep 1
    echo -ne "."
done
echo ""

# Démarrer Agence
echo -e "${BLUE}[2/2]${NC} Démarrage Agence (Port 8081)..."
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence.log 2>&1 &
AGENCE_PID=$!
echo -e "${GREEN}✓${NC} Agence démarrée (PID: $AGENCE_PID)"

# Attendre que le service soit prêt
echo -ne "${YELLOW}  Attente du service...${NC}"
for i in {1..30}; do
    if curl -s "http://localhost:8081/ws?wsdl" > /dev/null 2>&1; then
        echo -e " ${GREEN}✓${NC}"
        break
    fi
    sleep 1
    echo -ne "."
done
echo ""

echo "═══════════════════════════════════════════════════════════"
echo -e "${GREEN}✓ Services démarrés avec succès${NC}"
echo ""
echo "Services actifs:"
echo "  • Hotellerie Paris: http://localhost:8082/ws"
echo "  • Agence:          http://localhost:8081/ws"
echo ""
echo "Logs:"
echo "  • tail -f /tmp/hotellerie-paris.log"
echo "  • tail -f /tmp/agence.log"
echo ""
echo -e "${YELLOW}Appuyez sur Ctrl+C pour arrêter les services${NC}"
echo "═══════════════════════════════════════════════════════════"

# Garder le script actif
wait

