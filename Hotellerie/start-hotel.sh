#!/bin/bash

echo "========================================="
echo "Démarrage du Service SOAP Hotellerie"
echo "========================================="
echo ""
echo "Port: 8082"
echo "WSDL: http://localhost:8082/ws/hotel.wsdl"
echo ""

cd "$(dirname "$0")"
mvn spring-boot:run

