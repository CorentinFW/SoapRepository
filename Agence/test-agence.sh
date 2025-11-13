#!/bin/bash

# Script de test pour l'Agence

echo "========================================"
echo "Tests de l'Agence SOAP/REST"
echo "========================================"
echo ""

# Couleurs pour l'affichage
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# URL de base
BASE_URL="http://localhost:8081"

# Test 1 : Ping
echo -e "${YELLOW}Test 1 : Ping${NC}"
echo "GET $BASE_URL/api/agence/ping"
response=$(curl -s -w "\n%{http_code}" $BASE_URL/api/agence/ping)
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Réponse: $body"
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

# Test 2 : Recherche de chambres
echo -e "${YELLOW}Test 2 : Recherche de chambres${NC}"
echo "POST $BASE_URL/api/agence/rechercher"
request_body='{
  "adresse": "Paris",
  "dateArrive": "2025-12-01",
  "dateDepart": "2025-12-05",
  "prixMin": 0,
  "prixMax": 200,
  "nbrEtoile": 4,
  "nbrLits": 2
}'
echo "Requête: $request_body"

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL/api/agence/rechercher \
  -H "Content-Type: application/json" \
  -d "$request_body")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Réponse: $body"
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

# Test 3 : Réservation
echo -e "${YELLOW}Test 3 : Réservation${NC}"
echo "POST $BASE_URL/api/agence/reserver"
request_body='{
  "clientNom": "Dupont",
  "clientPrenom": "Jean",
  "clientNumeroCarteBleue": "1234567890123456",
  "chambreId": 1,
  "hotelAdresse": "Rue de la Paix, Paris",
  "dateArrive": "2025-12-01",
  "dateDepart": "2025-12-05"
}'
echo "Requête: $request_body"

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL/api/agence/reserver \
  -H "Content-Type: application/json" \
  -d "$request_body")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Réponse: $body"
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

# Test 4 : Réservation invalide (sans nom)
echo -e "${YELLOW}Test 4 : Réservation invalide (validation)${NC}"
echo "POST $BASE_URL/api/agence/reserver"
request_body='{
  "clientNom": "",
  "clientPrenom": "Jean",
  "chambreId": 1,
  "dateArrive": "2025-12-01",
  "dateDepart": "2025-12-05"
}'
echo "Requête: $request_body"

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL/api/agence/reserver \
  -H "Content-Type: application/json" \
  -d "$request_body")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "400" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Validation OK - Status: $http_code"
    echo "  Réponse: $body"
else
    echo -e "${RED}✗ UNEXPECTED${NC} - Status: $http_code (attendu: 400)"
    echo "  Réponse: $body"
fi
echo ""

echo "========================================"
echo "Tests terminés"
echo "========================================"

