#!/bin/bash

echo "========================================="
echo "Démarrage du Client CLI"
echo "========================================="
echo ""
echo "Connexion à l'agence: http://localhost:8081"
echo ""

cd "$(dirname "$0")"
mvn spring-boot:run

