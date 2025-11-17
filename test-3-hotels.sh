#!/bin/bash

# Script de test des 3 hôtelleries

echo "═══════════════════════════════════════════════════════════"
echo "   Test des 3 Hôtelleries SOAP"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Test de l'hôtel Paris (Port 8082)
echo -e "${YELLOW}[1/3] Test Hotellerie Paris (Port 8082)${NC}"
if curl -s "http://localhost:8082/ws?wsdl" > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} WSDL accessible"
    echo "  URL: http://localhost:8082/ws?wsdl"
else
    echo -e "  ${RED}✗${NC} WSDL non accessible"
fi
echo ""

# Test de l'hôtel Lyon (Port 8083)
echo -e "${YELLOW}[2/3] Test Hotellerie Lyon (Port 8083)${NC}"
if curl -s "http://localhost:8083/ws?wsdl" > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} WSDL accessible"
    echo "  URL: http://localhost:8083/ws?wsdl"
else
    echo -e "  ${RED}✗${NC} WSDL non accessible"
fi
echo ""

# Test de l'hôtel Montpellier (Port 8084)
echo -e "${YELLOW}[3/3] Test Hotellerie Montpellier (Port 8084)${NC}"
if curl -s "http://localhost:8084/ws?wsdl" > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} WSDL accessible"
    echo "  URL: http://localhost:8084/ws?wsdl"
else
    echo -e "  ${RED}✗${NC} WSDL non accessible"
fi
echo ""

# Test de l'agence (Port 8081)
echo -e "${YELLOW}[4/4] Test Agence (Port 8081)${NC}"
if curl -s "http://localhost:8081/ws?wsdl" > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} WSDL accessible"
    echo "  URL: http://localhost:8081/ws?wsdl"
else
    echo -e "  ${RED}✗${NC} WSDL non accessible"
fi
echo ""

echo "═══════════════════════════════════════════════════════════"
echo -e "${GREEN}Test terminé${NC}"
echo "═══════════════════════════════════════════════════════════"

