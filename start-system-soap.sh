#!/bin/bash

# Script de démarrage du système de réservation hôtelière SOAP avec 3 hôtels

echo "═══════════════════════════════════════════════════════════"
echo "   Système de Réservation Hôtelière - Architecture SOAP"
echo "   3 Hôtels: Paris, Lyon, Montpellier"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Répertoire de base
BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# Fonction pour attendre qu'un service soit prêt
wait_for_service() {
    local port=$1
    local name=$2
    local max_attempts=30
    local attempt=0

    echo -ne "${YELLOW}  Attente démarrage $name...${NC}"
    while [ $attempt -lt $max_attempts ]; do
        if curl -s "http://localhost:$port/ws" > /dev/null 2>&1 || \
           curl -s "http://localhost:$port/ws?wsdl" > /dev/null 2>&1; then
            echo -e " ${GREEN}✓${NC}"
            return 0
        fi
        sleep 1
        attempt=$((attempt + 1))
        echo -ne "."
    done
    echo -e " ${RED}✗ Timeout${NC}"
    return 1
}

# 1. Démarrer Hotellerie Paris (Port 8082)
echo -e "${BLUE}[1/4]${NC} Démarrage de Hotellerie Paris (Port 8082)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=paris > /tmp/hotellerie-paris.log 2>&1 &
HOTELLERIE_PARIS_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie Paris démarrée (PID: $HOTELLERIE_PARIS_PID)"
wait_for_service 8082 "Paris"

# 2. Démarrer Hotellerie Lyon (Port 8083)
echo -e "${BLUE}[2/4]${NC} Démarrage de Hotellerie Lyon (Port 8083)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=lyon > /tmp/hotellerie-lyon.log 2>&1 &
HOTELLERIE_LYON_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie Lyon démarrée (PID: $HOTELLERIE_LYON_PID)"
wait_for_service 8083 "Lyon"

# 3. Démarrer Hotellerie Montpellier (Port 8084)
echo -e "${BLUE}[3/4]${NC} Démarrage de Hotellerie Montpellier (Port 8084)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier > /tmp/hotellerie-montpellier.log 2>&1 &
HOTELLERIE_MONTPELLIER_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie Montpellier démarrée (PID: $HOTELLERIE_MONTPELLIER_PID)"
wait_for_service 8084 "Montpellier"

# 4. Démarrer Agence (Port 8081)
echo -e "${BLUE}[4/4]${NC} Démarrage de Agence (Port 8081)..."
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence.log 2>&1 &
AGENCE_PID=$!
echo -e "${GREEN}✓${NC} Agence démarrée (PID: $AGENCE_PID)"
wait_for_service 8081 "Agence"

# Attendre encore 5 secondes pour que l'agence soit complètement initialisée
echo -e "${YELLOW}  Finalisation de l'initialisation de l'agence...${NC}"
sleep 5

# 5. Démarrer Client
echo ""
echo "═══════════════════════════════════════════════════════════"
echo -e "${GREEN}✓ Tous les services sont démarrés${NC}"
echo ""
echo "Services actifs:"
echo "  • Hotellerie Paris:       http://localhost:8082/ws"
echo "  • Hotellerie Lyon:        http://localhost:8083/ws"
echo "  • Hotellerie Montpellier: http://localhost:8084/ws"
echo "  • Agence:                 http://localhost:8081/ws"
echo ""
echo "Logs disponibles:"
echo "  • tail -f /tmp/hotellerie-paris.log"
echo "  • tail -f /tmp/hotellerie-lyon.log"
echo "  • tail -f /tmp/hotellerie-montpellier.log"
echo "  • tail -f /tmp/agence.log"
echo ""
echo "═══════════════════════════════════════════════════════════"
echo ""
echo -e "${CYAN}Démarrage du client CLI...${NC}"
echo ""

cd "$BASE_DIR/Client"
mvn spring-boot:run

# Nettoyage à la sortie
echo ""
echo -e "${YELLOW}Arrêt des services...${NC}"
kill $HOTELLERIE_PARIS_PID $HOTELLERIE_LYON_PID $HOTELLERIE_MONTPELLIER_PID $AGENCE_PID 2>/dev/null
echo -e "${GREEN}✓${NC} Services arrêtés"



