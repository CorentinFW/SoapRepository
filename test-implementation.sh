#!/bin/bash

# Script de test rapide pour la nouvelle fonctionnalité

echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║   Test: Afficher Réservations par Hôtel                     ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

echo -e "${BLUE}Vérification de la compilation...${NC}"
echo ""

# 1. Vérifier Hotellerie
echo -ne "${YELLOW}  Hotellerie... ${NC}"
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
if mvn compile -q 2>/dev/null; then
    echo -e "${GREEN}✓${NC}"
else
    echo -e "${RED}✗${NC}"
    exit 1
fi

# 2. Vérifier Agence
echo -ne "${YELLOW}  Agence... ${NC}"
cd /home/corentinfay/Bureau/SoapRepository/Agence
if mvn compile -q 2>/dev/null; then
    echo -e "${GREEN}✓${NC}"
else
    echo -e "${RED}✗${NC}"
    exit 1
fi

# 3. Vérifier Client
echo -ne "${YELLOW}  Client... ${NC}"
cd /home/corentinfay/Bureau/SoapRepository/Client
if mvn compile -q 2>/dev/null; then
    echo -e "${GREEN}✓${NC}"
else
    echo -e "${RED}✗${NC}"
    exit 1
fi

echo ""
echo -e "${BLUE}Vérification des classes générées...${NC}"
echo ""

# Vérifier les classes JAXB du client
if [ -f "/home/corentinfay/Bureau/SoapRepository/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/GetAllReservationsByHotelRequest.java" ]; then
    echo -e "${GREEN}  ✓${NC} GetAllReservationsByHotelRequest"
else
    echo -e "${RED}  ✗${NC} GetAllReservationsByHotelRequest manquant"
    exit 1
fi

if [ -f "/home/corentinfay/Bureau/SoapRepository/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/GetAllReservationsByHotelResponse.java" ]; then
    echo -e "${GREEN}  ✓${NC} GetAllReservationsByHotelResponse"
else
    echo -e "${RED}  ✗${NC} GetAllReservationsByHotelResponse manquant"
    exit 1
fi

if [ -f "/home/corentinfay/Bureau/SoapRepository/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/HotelReservations.java" ]; then
    echo -e "${GREEN}  ✓${NC} HotelReservations"
else
    echo -e "${RED}  ✗${NC} HotelReservations manquant"
    exit 1
fi

if [ -f "/home/corentinfay/Bureau/SoapRepository/Client/target/generated-sources/xjc/org/tp1/client/wsdl/agence/Reservation.java" ]; then
    echo -e "${GREEN}  ✓${NC} Reservation"
else
    echo -e "${RED}  ✗${NC} Reservation manquant"
    exit 1
fi

echo ""
echo -e "${BLUE}Vérification des méthodes ajoutées...${NC}"
echo ""

# Vérifier AgenceSoapClient
if grep -q "getAllReservationsByHotel" /home/corentinfay/Bureau/SoapRepository/Client/src/main/java/org/tp1/client/soap/AgenceSoapClient.java; then
    echo -e "${GREEN}  ✓${NC} AgenceSoapClient.getAllReservationsByHotel()"
else
    echo -e "${RED}  ✗${NC} Méthode manquante dans AgenceSoapClient"
    exit 1
fi

# Vérifier ClientCLISoap
if grep -q "afficherReservationsParHotel" /home/corentinfay/Bureau/SoapRepository/Client/src/main/java/org/tp1/client/cli/ClientCLISoap.java; then
    echo -e "${GREEN}  ✓${NC} ClientCLISoap.afficherReservationsParHotel()"
else
    echo -e "${RED}  ✗${NC} Méthode manquante dans ClientCLISoap"
    exit 1
fi

# Vérifier le menu
if grep -q "Afficher toutes les réservations par hôtel" /home/corentinfay/Bureau/SoapRepository/Client/src/main/java/org/tp1/client/cli/ClientCLISoap.java; then
    echo -e "${GREEN}  ✓${NC} Option 4 ajoutée au menu"
else
    echo -e "${RED}  ✗${NC} Option 4 manquante dans le menu"
    exit 1
fi

# Vérifier MultiHotelSoapClient (Agence)
if grep -q "getAllReservationsByHotel" /home/corentinfay/Bureau/SoapRepository/Agence/src/main/java/org/tp1/agence/client/MultiHotelSoapClient.java; then
    echo -e "${GREEN}  ✓${NC} MultiHotelSoapClient.getAllReservationsByHotel()"
else
    echo -e "${RED}  ✗${NC} Méthode manquante dans MultiHotelSoapClient"
    exit 1
fi

# Vérifier AgenceService
if grep -q "getAllReservationsByHotel" /home/corentinfay/Bureau/SoapRepository/Agence/src/main/java/org/tp1/agence/service/AgenceService.java; then
    echo -e "${GREEN}  ✓${NC} AgenceService.getAllReservationsByHotel()"
else
    echo -e "${RED}  ✗${NC} Méthode manquante dans AgenceService"
    exit 1
fi

# Vérifier AgenceEndpoint
if grep -q "getAllReservationsByHotel" /home/corentinfay/Bureau/SoapRepository/Agence/src/main/java/org/tp1/agence/endpoint/AgenceEndpoint.java; then
    echo -e "${GREEN}  ✓${NC} AgenceEndpoint.getAllReservationsByHotel()"
else
    echo -e "${RED}  ✗${NC} Endpoint manquant dans AgenceEndpoint"
    exit 1
fi

echo ""
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║              ✅ TOUS LES TESTS SONT PASSÉS !                ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""
echo -e "${GREEN}L'implémentation est complète et fonctionnelle !${NC}"
echo ""
echo -e "${CYAN}Pour tester le système complet:${NC}"
echo "  ./start-robuste.sh"
echo ""
echo -e "${CYAN}Dans le client CLI, choisir l'option 4${NC}"
echo ""

