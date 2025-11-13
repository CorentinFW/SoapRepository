#!/bin/bash

# Script de test pour le service SOAP Hotellerie

echo "========================================"
echo "Tests du Service SOAP Hotellerie"
echo "========================================"
echo ""

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

# URL de base
BASE_URL="http://localhost:8082/ws"

# Test 1 : Vérifier que le WSDL est accessible
echo -e "${YELLOW}Test 1 : Accès au WSDL${NC}"
echo "GET $BASE_URL/hotel.wsdl"
response=$(curl -s -w "\n%{http_code}" $BASE_URL/hotel.wsdl)
http_code=$(echo "$response" | tail -n1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - WSDL accessible"
    echo "  Les 5 premières lignes du WSDL:"
    echo "$response" | head -n-1 | head -5
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Le service SOAP ne semble pas démarré"
    exit 1
fi
echo ""

# Test 2 : Obtenir les informations de l'hôtel
echo -e "${YELLOW}Test 2 : Obtenir les informations de l'hôtel${NC}"
echo "POST $BASE_URL"
soap_request='<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:getHotelInfoRequest/>
   </soapenv:Body>
</soapenv:Envelope>'

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -d "$soap_request")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Réponse (extrait):"
    echo "$body" | grep -o '<hot:nom>[^<]*</hot:nom>' || echo "$body" | head -10
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

# Test 3 : Recherche de chambres
echo -e "${YELLOW}Test 3 : Recherche de chambres${NC}"
echo "POST $BASE_URL"
soap_request='<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:rechercherChambresRequest>
         <hot:adresse>Paris</hot:adresse>
         <hot:dateArrive>2025-12-01</hot:dateArrive>
         <hot:dateDepart>2025-12-05</hot:dateDepart>
         <hot:prixMin>0</hot:prixMin>
         <hot:prixMax>200</hot:prixMax>
         <hot:nbrLits>2</hot:nbrLits>
      </hot:rechercherChambresRequest>
   </soapenv:Body>
</soapenv:Envelope>'

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -d "$soap_request")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Chambres trouvées:"
    echo "$body" | grep -o '<hot:nom>[^<]*</hot:nom>' || echo "  Voir réponse XML"
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

# Test 4 : Effectuer une réservation
echo -e "${YELLOW}Test 4 : Effectuer une réservation${NC}"
echo "POST $BASE_URL"
soap_request='<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hot="http://tp1.org/hotellerie/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <hot:effectuerReservationRequest>
         <hot:client>
            <hot:nom>Dupont</hot:nom>
            <hot:prenom>Jean</hot:prenom>
            <hot:numeroCarteBleue>1234567890123456</hot:numeroCarteBleue>
         </hot:client>
         <hot:chambreId>1</hot:chambreId>
         <hot:dateArrive>2025-12-10</hot:dateArrive>
         <hot:dateDepart>2025-12-15</hot:dateDepart>
      </hot:effectuerReservationRequest>
   </soapenv:Body>
</soapenv:Envelope>'

response=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL \
  -H "Content-Type: text/xml;charset=UTF-8" \
  -d "$soap_request")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo -e "${GREEN}✓ SUCCESS${NC} - Status: $http_code"
    echo "  Résultat:"
    echo "$body" | grep -o '<hot:success>[^<]*</hot:success>' || echo "  Réservation effectuée"
    echo "$body" | grep -o '<hot:message>[^<]*</hot:message>' || echo ""
else
    echo -e "${RED}✗ FAILED${NC} - Status: $http_code"
    echo "  Réponse: $body"
fi
echo ""

echo "========================================"
echo "Tests terminés"
echo "========================================"

