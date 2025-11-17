#!/bin/bash

# Script de démarrage ROBUSTE avec vérifications complètes

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║   Système Multi-Hôtels - Démarrage Robuste              ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

BASE_DIR="/home/corentinfay/Bureau/SoapRepository"

# Fonction cleanup
cleanup() {
    echo ""
    echo -e "${YELLOW}═══ Arrêt des services ═══${NC}"
    pkill -f "spring-boot:run.*paris" 2>/dev/null
    pkill -f "spring-boot:run.*lyon" 2>/dev/null
    pkill -f "spring-boot:run.*montpellier" 2>/dev/null
    pkill -f "Agence.*spring-boot" 2>/dev/null
    pkill -f "Client.*spring-boot" 2>/dev/null
    sleep 2
    echo -e "${GREEN}✓${NC} Services arrêtés"
}

trap cleanup EXIT INT TERM

# Fonction pour vérifier qu'un service Spring Boot répond vraiment
wait_for_spring_service() {
    local port=$1
    local name=$2
    local max_attempts=60  # Augmenté à 60 secondes
    local attempt=0

    echo -ne "${YELLOW}  ⏳ Attente démarrage $name (port $port)...${NC}"

    while [ $attempt -lt $max_attempts ]; do
        # Essayer plusieurs endpoints possibles
        if curl -s --max-time 2 "http://localhost:$port/ws?wsdl" > /dev/null 2>&1; then
            echo -e " ${GREEN}✓ Prêt!${NC}"
            return 0
        elif curl -s --max-time 2 "http://localhost:$port/ws" > /dev/null 2>&1; then
            echo -e " ${GREEN}✓ Prêt!${NC}"
            return 0
        elif nc -z localhost $port 2>/dev/null; then
            # Le port écoute, mais le service n'est pas encore prêt
            sleep 1
            attempt=$((attempt + 1))
            echo -ne "."
        else
            sleep 1
            attempt=$((attempt + 1))
            echo -ne "."
        fi
    done

    echo -e " ${RED}✗ Timeout après ${max_attempts}s${NC}"
    echo -e "${RED}Consultez les logs: tail -f /tmp/$name.log${NC}"
    return 1
}

# Vérifier que les ports sont libres
echo -e "${BLUE}[0/5]${NC} Vérification des ports..."
for port in 8081 8082 8083 8084; do
    if netstat -tuln 2>/dev/null | grep -q ":$port "; then
        echo -e "${RED}✗ Port $port déjà utilisé${NC}"
        echo "  Libérez le port avec: sudo kill -9 \$(sudo lsof -t -i:$port)"
        exit 1
    fi
done
echo -e "${GREEN}✓${NC} Tous les ports sont disponibles"
echo ""

# 1. Démarrer Hotellerie Paris
echo -e "${BLUE}[1/5]${NC} ${BOLD}Démarrage Hotellerie Paris${NC}"
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=paris > /tmp/hotellerie-paris.log 2>&1 &
PARIS_PID=$!
echo "  PID: $PARIS_PID"
if ! wait_for_spring_service 8082 "hotellerie-paris"; then
    echo -e "${RED}Échec du démarrage de Paris${NC}"
    exit 1
fi
echo ""

# 2. Démarrer Hotellerie Lyon
echo -e "${BLUE}[2/5]${NC} ${BOLD}Démarrage Hotellerie Lyon${NC}"
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=lyon > /tmp/hotellerie-lyon.log 2>&1 &
LYON_PID=$!
echo "  PID: $LYON_PID"
if ! wait_for_spring_service 8083 "hotellerie-lyon"; then
    echo -e "${RED}Échec du démarrage de Lyon${NC}"
    exit 1
fi
echo ""

# 3. Démarrer Hotellerie Montpellier
echo -e "${BLUE}[3/5]${NC} ${BOLD}Démarrage Hotellerie Montpellier${NC}"
cd "$BASE_DIR/Hotellerie"
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier > /tmp/hotellerie-montpellier.log 2>&1 &
MONTPELLIER_PID=$!
echo "  PID: $MONTPELLIER_PID"
if ! wait_for_spring_service 8084 "hotellerie-montpellier"; then
    echo -e "${RED}Échec du démarrage de Montpellier${NC}"
    exit 1
fi
echo ""

# Attendre un peu pour que les hôtels soient vraiment stabilisés
echo -e "${YELLOW}  ⏳ Stabilisation des hôtels...${NC}"
sleep 3
echo -e "${GREEN}  ✓ Hôtels stabilisés${NC}"
echo ""

# 4. Démarrer Agence
echo -e "${BLUE}[4/5]${NC} ${BOLD}Démarrage Agence${NC}"
cd "$BASE_DIR/Agence"
mvn spring-boot:run > /tmp/agence.log 2>&1 &
AGENCE_PID=$!
echo "  PID: $AGENCE_PID"
if ! wait_for_spring_service 8081 "agence"; then
    echo -e "${RED}Échec du démarrage de l'Agence${NC}"
    echo "Vérifiez que les hôtels sont accessibles:"
    curl -s http://localhost:8082/ws?wsdl > /dev/null && echo "  ✓ Paris OK" || echo "  ✗ Paris KO"
    curl -s http://localhost:8083/ws?wsdl > /dev/null && echo "  ✓ Lyon OK" || echo "  ✗ Lyon KO"
    curl -s http://localhost:8084/ws?wsdl > /dev/null && echo "  ✓ Montpellier OK" || echo "  ✗ Montpellier KO"
    exit 1
fi
echo ""

# Attendre que l'agence soit vraiment prête
echo -e "${YELLOW}  ⏳ Finalisation de l'agence...${NC}"
sleep 5
echo -e "${GREEN}  ✓ Agence prête${NC}"
echo ""

# Vérification finale de tous les services
echo -e "${CYAN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║              Vérification Finale des Services            ║${NC}"
echo -e "${CYAN}╚═══════════════════════════════════════════════════════════╝${NC}"
echo ""

all_ok=true

# Paris
if curl -s --max-time 5 http://localhost:8082/ws?wsdl > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} Paris        → http://localhost:8082/ws"
else
    echo -e "  ${RED}✗${NC} Paris        → ÉCHEC"
    all_ok=false
fi

# Lyon
if curl -s --max-time 5 http://localhost:8083/ws?wsdl > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} Lyon         → http://localhost:8083/ws"
else
    echo -e "  ${RED}✗${NC} Lyon         → ÉCHEC"
    all_ok=false
fi

# Montpellier
if curl -s --max-time 5 http://localhost:8084/ws?wsdl > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} Montpellier  → http://localhost:8084/ws"
else
    echo -e "  ${RED}✗${NC} Montpellier  → ÉCHEC"
    all_ok=false
fi

# Agence
if curl -s --max-time 5 http://localhost:8081/ws?wsdl > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓${NC} Agence       → http://localhost:8081/ws"
else
    echo -e "  ${RED}✗${NC} Agence       → ÉCHEC"
    all_ok=false
fi

echo ""

if [ "$all_ok" = false ]; then
    echo -e "${RED}═══ ERREUR: Un ou plusieurs services ne répondent pas ═══${NC}"
    echo ""
    echo "Consultez les logs:"
    echo "  tail -f /tmp/hotellerie-paris.log"
    echo "  tail -f /tmp/hotellerie-lyon.log"
    echo "  tail -f /tmp/hotellerie-montpellier.log"
    echo "  tail -f /tmp/agence.log"
    exit 1
fi

echo -e "${GREEN}═══ TOUS LES SERVICES SONT OPÉRATIONNELS ═══${NC}"
echo ""
echo "Logs disponibles:"
echo "  • tail -f /tmp/hotellerie-paris.log"
echo "  • tail -f /tmp/hotellerie-lyon.log"
echo "  • tail -f /tmp/hotellerie-montpellier.log"
echo "  • tail -f /tmp/agence.log"
echo ""

# 5. Démarrer Client
echo -e "${BLUE}[5/5]${NC} ${BOLD}Démarrage du Client CLI${NC}"
echo ""
echo -e "${CYAN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║                    CLIENT CLI SOAP                        ║${NC}"
echo -e "${CYAN}╚═══════════════════════════════════════════════════════════╝${NC}"
echo ""

cd "$BASE_DIR/Client"
mvn spring-boot:run

# Le cleanup sera appelé automatiquement à la sortie

