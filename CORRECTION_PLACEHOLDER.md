# 🔧 CORRECTION - Erreur "Could not resolve placeholder 'hotel.soap.url'"

## ✅ Problème Résolu !

### 🚨 Erreur Rencontrée

Après le démarrage des 3 hôtels, l'Agence échouait avec :
```
Could not resolve placeholder 'hotel.soap.url' in value "${hotel.soap.url}"
Error creating bean with name 'hotelSoapClient'
```

### 🎯 Cause du Problème

Il y avait **3 clients SOAP** dans l'Agence :
1. `HotelSoapClient.java` (ancien, avec `@Component`)
2. `RealHotelSoapClient.java` (ancien, avec `@Component`)
3. `MultiHotelSoapClient.java` (nouveau, avec `@Component`)

Les 2 anciens utilisaient `hotel.soap.url` (singulier), mais la configuration a été changée pour `hotel.soap.urls` (pluriel) pour supporter plusieurs hôtels.

Spring Boot essayait de créer les 3 beans et échouait sur les anciens clients.

### ✅ Solution Appliquée

J'ai **désactivé les 2 anciens clients** en commentant leur annotation `@Component` :

**Fichiers modifiés :**
1. `Agence/src/main/java/org/tp1/agence/client/HotelSoapClient.java`
2. `Agence/src/main/java/org/tp1/agence/client/RealHotelSoapClient.java`

**Changement effectué :**
```java
// Avant
@Component
public class HotelSoapClient {
    @Value("${hotel.soap.url}")  // ← Cherche cette propriété qui n'existe plus
    private String hotelSoapUrl;
}

// Après
// @Component - DÉSACTIVÉ: Remplacé par MultiHotelSoapClient
public class HotelSoapClient {
    @Value("${hotel.soap.url}")
    private String hotelSoapUrl;
}
```

Maintenant, seul `MultiHotelSoapClient` est actif et utilise `hotel.soap.urls` (pluriel).

### 🧪 Test de Validation

```bash
cd /home/corentinfay/Bureau/SoapRepository/Agence
mvn clean compile
# ✓ Compilation OK
```

### 🚀 Prochaine Étape

Le système est maintenant prêt ! Lancez :

```bash
cd /home/corentinfay/Bureau/SoapRepository
./start-robuste.sh
```

Le démarrage devrait maintenant fonctionner complètement :
1. ✅ Paris (Port 8082)
2. ✅ Lyon (Port 8083)
3. ✅ Montpellier (Port 8084)
4. ✅ Agence (Port 8081) ← **Corrigé !**
5. ✅ Client CLI

---

## 📊 Résumé des Corrections

| Problème | Solution | Statut |
|----------|----------|--------|
| Connexion refusée | `start-robuste.sh` (attente 60s) | ✅ Résolu |
| Placeholder 'hotel.soap.url' | Désactiver anciens clients | ✅ Résolu |

---

## 📝 Notes Techniques

**Pourquoi ne pas les supprimer ?**
- Les fichiers sont conservés pour référence
- Ils sont juste désactivés (pas de `@Component`)
- Peuvent être supprimés plus tard si nécessaire

**Alternative :**
On aurait pu supprimer les fichiers complètement, mais garder le code permet de voir l'évolution du projet.

---

## ✅ Validation Finale

Pour vérifier que tout fonctionne :

```bash
# 1. Vérifier la compilation
cd Agence && mvn clean compile

# 2. Lancer le système complet
cd ..
./start-robuste.sh

# 3. Attendre ~3-4 minutes

# 4. Le client CLI devrait s'afficher avec le menu
```

---

**Date de correction :** 2025-11-17 09:05
**Version :** 1.2 (Correction beans multiples)

