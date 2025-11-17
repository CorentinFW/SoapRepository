#!/bin/bash

# PREMIER TEST - Instructions pas à pas

clear
echo "╔════════════════════════════════════════════════════════════╗"
echo "║    🏨 SYSTÈME MULTI-HÔTELS - PREMIER TEST                ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "Ce script va vous guider pour votre premier test."
echo ""
echo "📋 ÉTAPES:"
echo "  1. Démarrer les 3 hôtelleries (Paris, Lyon, Montpellier)"
echo "  2. Démarrer l'agence"
echo "  3. Lancer le client CLI"
echo ""
echo "⏱️  TEMPS ESTIMÉ: 3-4 minutes (démarrage robuste)"
echo ""
echo "ℹ️  Le script va attendre que chaque service soit vraiment prêt"
echo "   avant de passer au suivant. C'est plus long mais 100% fiable."
echo ""
read -p "Appuyez sur ENTRÉE pour commencer..."
echo ""

# Lancer le système robuste
echo "🚀 Lancement du système robuste..."
echo ""
./start-robuste.sh

