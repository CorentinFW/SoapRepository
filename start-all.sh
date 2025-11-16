#!/bin/bash

# Script pour démarrer tout le système de réservation hôtelière
# Auteur: Système de Réservation Hôtelière
# Date: 2025-11-15

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m'

# Répertoire de base
BASE_DIR="$(cd "$(dirname "$0")" && pwd)"

echo -e "${CYAN}${BOLD}"
echo "╔════════════════════════════════════════════════════════╗"
echo "║                                                        ║"
echo "║   SYSTÈME DE RÉSERVATION HÔTELIÈRE - DÉMARRAGE COMPLET ║"
echo "║                                                        ║"
echo "╚════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Vérifier Java
echo -e "${YELLOW}Vérification des prérequis...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}✗ Java n'est pas installé${NC}"
    echo "Installez Java avec: sudo apt install openjdk-8-jdk"
    exit 1
fi
echo -e "${GREEN}✓ Java installé: $(java -version 2>&1 | head -n 1)${NC}"

# Vérifier Maven
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}✗ Maven n'est pas installé${NC}"
    echo "Installez Maven avec: sudo apt install maven"
    exit 1
fi
echo -e "${GREEN}✓ Maven installé: $(mvn -version 2>&1 | head -n 1)${NC}"

# Vérifier JDK
if ! command -v javac &> /dev/null; then
    echo -e "${RED}✗ JDK n'est pas installé (seulement JRE)${NC}"
    echo "Installez le JDK avec: sudo apt install openjdk-8-jdk"
    exit 1
fi
echo -e "${GREEN}✓ JDK installé${NC}"

echo ""
echo -e "${BOLD}${BLUE}Ce script va démarrer les 3 composants du système:${NC}"
echo -e "  1. ${YELLOW}Hotellerie${NC} (SOAP Server - Port 8082)"
echo -e "  2. ${YELLOW}Agence${NC} (REST Server - Port 8081)"
echo -e "  3. ${YELLOW}Client CLI${NC} (Interface utilisateur)"
echo ""
echo -e "${BOLD}Vous aurez besoin de ${RED}3 terminaux${NC}${BOLD} ouverts.${NC}"
echo ""

read -p "Appuyez sur ENTRÉE pour voir les commandes à exécuter..."

echo ""
echo -e "${CYAN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo -e "${BOLD}TERMINAL 1 - Démarrer l'Hotellerie (SOAP Server)${NC}"
echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo ""
echo -e "${YELLOW}cd $BASE_DIR/Hotellerie${NC}"
echo -e "${YELLOW}mvn spring-boot:run${NC}"
echo ""
echo -e "Attendez le message: ${GREEN}Started HotellerieApplication${NC}"
echo ""

read -p "Appuyez sur ENTRÉE pour continuer..."

echo ""
echo -e "${CYAN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo -e "${BOLD}TERMINAL 2 - Démarrer l'Agence (REST Server)${NC}"
echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo ""
echo -e "${YELLOW}cd $BASE_DIR/Agence${NC}"
echo -e "${YELLOW}mvn spring-boot:run${NC}"
echo ""
echo -e "Attendez le message: ${GREEN}Started AgenceApplication${NC}"
echo ""

read -p "Appuyez sur ENTRÉE pour continuer..."

echo ""
echo -e "${CYAN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo -e "${BOLD}TERMINAL 3 - Démarrer le Client CLI${NC}"
echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo ""
echo -e "${YELLOW}cd $BASE_DIR/Client${NC}"
echo -e "${YELLOW}mvn spring-boot:run${NC}"
echo ""
echo -e "Le menu interactif s'affichera automatiquement."
echo ""

echo ""
echo -e "${GREEN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo -e "${GREEN}${BOLD}ORDRE DE DÉMARRAGE COMPLET:${NC}"
echo -e "${GREEN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo ""
echo -e "${BOLD}1.${NC} Ouvrez ${BOLD}Terminal 1${NC} et démarrez ${YELLOW}Hotellerie${NC}"
echo -e "   ${YELLOW}cd $BASE_DIR/Hotellerie && mvn spring-boot:run${NC}"
echo ""
echo -e "${BOLD}2.${NC} Ouvrez ${BOLD}Terminal 2${NC} et démarrez ${YELLOW}Agence${NC}"
echo -e "   ${YELLOW}cd $BASE_DIR/Agence && mvn spring-boot:run${NC}"
echo ""
echo -e "${BOLD}3.${NC} Ouvrez ${BOLD}Terminal 3${NC} et démarrez ${YELLOW}Client${NC}"
echo -e "   ${YELLOW}cd $BASE_DIR/Client && mvn spring-boot:run${NC}"
echo ""
echo -e "${GREEN}${BOLD}Profitez du système de réservation hôtelière ! 🎉${NC}"
echo ""

# Tests rapides
echo ""
echo -e "${CYAN}${BOLD}═══════════════════════════════════════════════════${NC}"
echo -e "${BOLD}TESTS RAPIDES (après démarrage):${NC}"
echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo ""
echo -e "${BOLD}Test Hotellerie:${NC}"
echo -e "${YELLOW}curl http://localhost:8082/ws/hotel.wsdl${NC}"
echo ""
echo -e "${BOLD}Test Agence:${NC}"
echo -e "${YELLOW}curl http://localhost:8081/api/agence/ping${NC}"
echo ""
echo -e "${BOLD}Test complet:${NC}"
echo -e "${YELLOW}Utilisez le menu du Client CLI${NC}"
echo ""

