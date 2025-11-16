#!/bin/bash

# Script de démarrage du système de réservation hôtelière SOAP

echo "═══════════════════════════════════════════════════════════"
echo "   Système de Réservation Hôtelière - Architecture SOAP"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Répertoire de base
BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# 1. Démarrer Hotellerie
echo -e "${YELLOW}[1/3]${NC} Démarrage de Hotellerie (Port 8082)..."
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run > /tmp/hotellerie.log 2>&1 &
HOTELLERIE_PID=$!
echo -e "${GREEN}✓${NC} Hotellerie démarrée (PID: $HOTELLERIE_PID)"
sleep 5

# 2. Démarrer Agence
echo -e "${YELLOW}[2/3]${NC} Démarrage de Agence (Port 8081)..."
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence.log 2>&1 &
AGENCE_PID=$!
echo -e "${GREEN}✓${NC} Agence démarrée (PID: $AGENCE_PID)"
sleep 5

# 3. Démarrer Client
echo -e "${YELLOW}[3/3]${NC} Démarrage du Client (CLI)..."
echo ""
echo "═══════════════════════════════════════════════════════════"
echo ""

cd "$BASE_DIR/Client"
mvn spring-boot:run

# Nettoyage à la sortie
echo ""
echo -e "${YELLOW}Arrêt des services...${NC}"
kill $HOTELLERIE_PID $AGENCE_PID 2>/dev/null
echo -e "${GREEN}✓${NC} Services arrêtés"

