# 🔧 GUIDE DE DÉPANNAGE - Erreur "Connexion refusée"

## 🚨 Problème

```
Erreur: I/O error: Connexion refusée (Connection refused)
nested exception is java.net.ConnectException: Connexion refusée
```

---

## 🎯 Causes Possibles

### 1. **Services pas encore démarrés** (cause la plus fréquente)
Les services Spring Boot mettent 20-30 secondes à démarrer. Si le client essaie de se connecter trop tôt, il obtient cette erreur.

### 2. **Services crashés au démarrage**
Un service a démarré mais a planté immédiatement.

### 3. **Ports déjà utilisés**
Un autre processus utilise les ports 8081-8084.

### 4. **Configuration incorrecte**
Les URLs dans `application.properties` ne correspondent pas aux services réels.

---

## ✅ SOLUTIONS

### Solution 1: Utiliser le script robuste (RECOMMANDÉ)

```bash
./start-robuste.sh
```

Ce script :
- ✅ Vérifie que les ports sont libres
- ✅ Attend 60 secondes par service
- ✅ Vérifie que chaque service répond vraiment
- ✅ Stabilise avant de passer au suivant
- ✅ Affiche une vérification finale

**Temps total: ~3-4 minutes** (mais 100% fiable)

---

### Solution 2: Augmenter les timeouts dans les scripts existants

Le problème est que les scripts actuels attendent seulement 30 secondes, ce qui peut ne pas suffire.

**Modification à faire dans `start-system-soap.sh`:**

Ligne 24, remplacer:
```bash
local max_attempts=30
```

Par:
```bash
local max_attempts=60  # 60 secondes au lieu de 30
```

---

### Solution 3: Démarrer manuellement avec attentes

```bash
# Terminal 1 - Paris
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris

# Attendre le message "Started HotellerieApplication in X seconds"

# Terminal 2 - Lyon  
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=lyon

# Attendre le message de démarrage

# Terminal 3 - Montpellier
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier

# Attendre le message de démarrage

# Terminal 4 - Agence
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn spring-boot:run

# Attendre le message de démarrage ET vérifier qu'elle se connecte aux 3 hôtels

# Terminal 5 - Client
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

---

### Solution 4: Vérifier manuellement avant de lancer le client

```bash
# 1. Lancer les services en arrière-plan
./start-system-soap.sh

# 2. Dans un autre terminal, vérifier avec curl
curl http://localhost:8082/ws?wsdl  # Paris
curl http://localhost:8083/ws?wsdl  # Lyon
curl http://localhost:8084/ws?wsdl  # Montpellier
curl http://localhost:8081/ws?wsdl  # Agence

# Si tous répondent OK (du XML WSDL), alors lancer le client:
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

---

## 🔍 Diagnostic

### Étape 1: Vérifier les logs

```bash
# Vérifier si Paris a démarré
tail -50 /tmp/hotellerie-paris.log | grep -i "started\|error"

# Vérifier si Lyon a démarré
tail -50 /tmp/hotellerie-lyon.log | grep -i "started\|error"

# Vérifier si Montpellier a démarré
tail -50 /tmp/hotellerie-montpellier.log | grep -i "started\|error"

# Vérifier si l'Agence a démarré
tail -50 /tmp/agence.log | grep -i "started\|error"
```

**Message de succès attendu:**
```
Started HotellerieApplication in 23.456 seconds
```

**Message d'erreur typique:**
```
Connection refused
Port already in use
```

### Étape 2: Vérifier les ports

```bash
# Voir quels ports écoutent
netstat -tuln | grep "808[1-4]"

# Résultat attendu:
# tcp6  0  0 :::8081  :::*  LISTEN  (Agence)
# tcp6  0  0 :::8082  :::*  LISTEN  (Paris)
# tcp6  0  0 :::8083  :::*  LISTEN  (Lyon)
# tcp6  0  0 :::8084  :::*  LISTEN  (Montpellier)
```

### Étape 3: Tester les services

```bash
# Test simple
for port in 8081 8082 8083 8084; do
    echo -n "Port $port: "
    curl -s --max-time 5 http://localhost:$port/ws?wsdl > /dev/null && echo "OK" || echo "KO"
done
```

---

## 🛠️ Corrections Spécifiques

### Si un port est déjà utilisé

```bash
# Trouver le processus
sudo lsof -i :8082

# Tuer le processus
sudo kill -9 <PID>

# Ou tuer tous les services Spring Boot
pkill -f spring-boot:run
```

### Si les services crashent au démarrage

```bash
# Voir l'erreur complète
cat /tmp/hotellerie-paris.log

# Erreurs courantes:
# - "Port already in use" → Libérer le port
# - "Cannot find property" → Vérifier application.properties
# - "ClassNotFoundException" → Recompiler: mvn clean compile
```

### Si l'Agence ne trouve pas les hôtels

**Vérifier la configuration:**
```bash
cat /home/corentinfay/Bureau/SoapRepository/Agence/src/main/resources/application.properties
```

**Doit contenir:**
```properties
hotel.soap.urls=http://localhost:8082/ws,http://localhost:8083/ws,http://localhost:8084/ws
```

---

## 📋 Checklist de Vérification

Avant de lancer le système, vérifiez:

- [ ] Ports 8081-8084 sont libres
- [ ] Compilation réussie (mvn clean compile)
- [ ] Pas de processus Java zombie
- [ ] Assez de RAM (4 services = ~2 GB)
- [ ] Pas d'antivirus bloquant les ports

---

## 🚀 Méthode INFAILLIBLE (Démarrage Progressif)

```bash
# 1. Nettoyer tout
pkill -f spring-boot
sleep 2

# 2. Paris SEUL
cd /home/corentinfay/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run -Dspring-boot.run.profiles=paris &
sleep 40  # Attendre 40 secondes

# 3. Vérifier Paris
curl http://localhost:8082/ws?wsdl
# Si OK → continuer, sinon → voir les logs

# 4. Lyon SEUL
mvn spring-boot:run -Dspring-boot.run.profiles=lyon &
sleep 40

# 5. Vérifier Lyon
curl http://localhost:8083/ws?wsdl

# 6. Montpellier SEUL
mvn spring-boot:run -Dspring-boot.run.profiles=montpellier &
sleep 40

# 7. Vérifier Montpellier
curl http://localhost:8084/ws?wsdl

# 8. Vérifier que TOUS les hôtels répondent
for port in 8082 8083 8084; do
    curl -s http://localhost:$port/ws?wsdl > /dev/null && echo "Port $port: OK" || echo "Port $port: KO"
done

# 9. Agence (seulement si TOUS les hôtels sont OK)
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn spring-boot:run &
sleep 40

# 10. Vérifier Agence
curl http://localhost:8081/ws?wsdl

# 11. Client (seulement si TOUT est OK)
cd /home/corentinfay/Bureau/SoapRepository/Client
mvn spring-boot:run
```

---

## 🎓 Comprendre le Problème

### Séquence de démarrage normale:

```
T+0s   : Lancement mvn spring-boot:run
T+5s   : Spring Boot démarre
T+10s  : Chargement des classes
T+15s  : Initialisation des beans
T+20s  : Serveur Tomcat démarre
T+25s  : Application prête ✓
```

### Ce qui se passe avec l'erreur:

```
T+0s   : Lancement Paris
T+5s   : Lancement Lyon
T+10s  : Lancement Montpellier
T+15s  : Lancement Agence ← ERREUR ICI!
T+20s  : Paris prêt
T+25s  : Lyon prêt
T+30s  : Montpellier prêt

→ L'Agence essaie de contacter les hôtels à T+15s
→ Mais ils ne sont pas encore prêts !
→ Connexion refusée
```

**Solution:** Attendre que chaque service soit VRAIMENT prêt avant de lancer le suivant.

---

## 📞 Si Rien ne Fonctionne

1. **Arrêter TOUT:**
   ```bash
   pkill -9 -f java
   ```

2. **Recompiler:**
   ```bash
   cd /home/corentinfay/Bureau/SoapRepository
   cd Hotellerie && mvn clean package -DskipTests && cd ..
   cd Agence && mvn clean package -DskipTests && cd ..
   cd Client && mvn clean package -DskipTests && cd ..
   ```

3. **Utiliser le script robuste:**
   ```bash
   ./start-robuste.sh
   ```

4. **Consulter les logs en temps réel:**
   ```bash
   tail -f /tmp/*.log
   ```

---

## ✅ Résumé

**Problème:** Services pas encore prêts quand le client essaie de se connecter

**Solution Simple:** `./start-robuste.sh` (attend assez longtemps)

**Solution Rapide:** Augmenter `max_attempts` à 60 dans les scripts

**Solution Manuelle:** Démarrer chaque service et attendre le message "Started"

---

**En cas de doute, consultez TOUJOURS les logs !**
```bash
tail -f /tmp/agence.log
```

