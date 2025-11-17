#!/bin/bash

# Script de test pour vérifier l'affichage "Mauvaise date"

echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║   Test: Affichage 'Mauvaise date' - Réservations            ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}Vérification de la modification...${NC}"
echo ""

# Vérifier que le code a été modifié
if grep -q "Mauvaise date" /home/corentinfay/Bureau/SoapRepository/Client/src/main/java/org/tp1/client/cli/ClientCLISoap.java; then
    echo -e "${GREEN}  ✓${NC} Message 'Mauvaise date' ajouté dans le code"
else
    echo -e "${RED}  ✗${NC} Message 'Mauvaise date' non trouvé"
    exit 1
fi

# Vérifier la détection par reservationId
if grep -q "getReservationId() == 0" /home/corentinfay/Bureau/SoapRepository/Client/src/main/java/org/tp1/client/cli/ClientCLISoap.java; then
    echo -e "${GREEN}  ✓${NC} Détection par reservationId == 0 présente"
else
    echo -e "${RED}  ✗${NC} Détection par reservationId == 0 manquante"
    exit 1
fi


echo ""
echo -e "${BLUE}Compilation du Client...${NC}"
echo ""

cd /home/corentinfay/Bureau/SoapRepository/Client
if mvn compile -q 2>/dev/null; then
    echo -e "${GREEN}  ✓${NC} Compilation réussie"
else
    echo -e "${RED}  ✗${NC} Erreur de compilation"
    exit 1
fi

echo ""
echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║              ✅ MODIFICATION VALIDÉE !                       ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""
echo -e "${GREEN}La correction est implémentée et compilée avec succès.${NC}"
echo ""
echo -e "${CYAN}Comment tester:${NC}"
echo "  1. Démarrer le système: ./start-robuste.sh"
echo "  2. Faire une première réservation (ex: 2025-12-01 → 2025-12-05)"
echo "  3. Essayer de réserver la même chambre avec dates chevauchantes"
echo "  4. Vérifier l'affichage: ${RED}✗ Mauvaise date${NC}"
echo ""
echo -e "${CYAN}Scénarios à tester:${NC}"
echo "  • Dates qui chevauchent → Affiche 'Mauvaise date'"
echo "  • Dates invalides → Affiche 'Mauvaise date'"
echo "  • Chambre non trouvée → Affiche raison complète"
echo "  • Client invalide → Affiche raison complète"
echo ""

