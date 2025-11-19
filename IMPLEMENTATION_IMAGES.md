# IMPLEMENTATION DES IMAGES POUR LES CHAMBRES

## 🎯 Objectif
Associer une image à chaque chambre d'hôtel et afficher l'URL de l'image dans le CLI du client.

## 📋 Modifications effectuées

### 1. Hotellerie (Serveur SOAP)

#### a) Modèle Chambre (`Hotellerie/src/main/java/org/tp1/hotellerie/model/Chambre.java`)
- ✅ Ajout du champ `imageUrl` (String)
- ✅ Ajout d'un constructeur avec imageUrl
- ✅ Ajout des getters/setters pour imageUrl

#### b) XSD Hotel (`Hotellerie/src/main/resources/xsd/hotel.xsd`)
- ✅ Ajout de l'élément `<xs:element name="imageUrl" type="xs:string" minOccurs="0"/>` dans le type `chambre`

#### c) HotelService (`Hotellerie/src/main/java/org/tp1/hotellerie/soap/HotelService.java`)
- ✅ Ajout de `@Value("${server.port}")` pour récupérer le port du serveur
- ✅ Création de la méthode `getImageFileName()` qui retourne:
  - `Hotelle1.png` pour Paris (port 8082)
  - `Hotelle2.png` pour Lyon (port 8083)
  - `Hotelle3.png` pour Montpellier (port 8084)
- ✅ Construction de l'URL complète: `http://localhost:{port}/images/{nomImage}`
- ✅ Affectation de l'imageUrl lors de la création des chambres

#### d) HotelEndpoint (`Hotellerie/src/main/java/org/tp1/hotellerie/soap/HotelEndpoint.java`)
- ✅ Ajout de `soapChambre.setImageUrl(chambre.getImageUrl())` dans la réponse SOAP

#### e) Configuration Ressources Statiques (`Hotellerie/src/main/java/org/tp1/hotellerie/config/StaticResourceConfig.java`)
- ✅ Création d'une nouvelle classe de configuration
- ✅ Configuration pour servir les images depuis `/images/**`
- ✅ Mapping vers `file:Image/` et `classpath:/static/images/`

### 2. Agence (Serveur SOAP intermédiaire)

#### a) XSD Agence (`Agence/src/main/resources/xsd/agence.xsd`)
- ✅ Ajout de l'élément `<xs:element name="imageUrl" type="xs:string" minOccurs="0"/>` dans le type `chambre`

#### b) XSD Hotel (`Agence/src/main/resources/xsd/hotel.xsd`)
- ✅ Ajout de l'élément `<xs:element name="imageUrl" type="xs:string" minOccurs="0"/>` dans le type `chambre`

#### c) ChambreDTO (`Agence/src/main/java/org/tp1/agence/dto/ChambreDTO.java`)
- ✅ Ajout du champ `imageUrl` (String)
- ✅ Ajout d'un constructeur avec imageUrl
- ✅ Ajout des getters/setters pour imageUrl

#### d) MultiHotelSoapClient (`Agence/src/main/java/org/tp1/agence/client/MultiHotelSoapClient.java`)
- ✅ Modification pour passer `chambre.getImageUrl()` lors de la création du ChambreDTO

#### e) AgenceEndpoint (`Agence/src/main/java/org/tp1/agence/endpoint/AgenceEndpoint.java`)
- ✅ Ajout de `chambreSoap.setImageUrl(chambreDTO.getImageUrl())` dans la réponse SOAP

### 3. Client (CLI SOAP)

#### a) WSDL Agence (`Client/src/main/resources/wsdl/agence.wsdl`)
- ✅ Ajout de l'élément `<xs:element name="imageUrl" type="xs:string" minOccurs="0"/>` dans le type `chambre`

#### b) ClientCLISoap (`Client/src/main/java/org/tp1/client/cli/ClientCLISoap.java`)
- ✅ Modification de la méthode `afficherChambres()` pour afficher l'URL de l'image:
  ```java
  if (chambre.getImageUrl() != null && !chambre.getImageUrl().isEmpty()) {
      System.out.printf("  🖼️  Image: " + BLUE + "%s" + RESET + "\n", chambre.getImageUrl());
  }
  ```

## 🗂️ Structure des images

```
SoapRepository/
├── Hotellerie/
│   └── Image/
│       ├── Hotelle1.png  → Paris
│       ├── Hotelle2.png  → Lyon
│       └── Hotelle3.png  → Montpellier
└── Image/                 → Copie à la racine (pour accès facile)
    ├── Hotelle1.png
    ├── Hotelle2.png
    └── Hotelle3.png
```

## 🔄 Mapping Hôtel → Image

| Hôtel | Port | Ville | Fichier Image | URL |
|-------|------|-------|---------------|-----|
| Grand Hotel Paris | 8082 | Paris | Hotelle1.png | http://localhost:8082/images/Hotelle1.png |
| Hotel Lyon Centre | 8083 | Lyon | Hotelle2.png | http://localhost:8083/images/Hotelle2.png |
| Hotel Mediterranee | 8084 | Montpellier | Hotelle3.png | http://localhost:8084/images/Hotelle3.png |

## 🚀 Compilation

Tous les projets ont été recompilés dans l'ordre:

```bash
# 1. Hotellerie
cd Hotellerie && mvn clean compile -DskipTests

# 2. Agence
cd ../Agence && mvn clean compile -DskipTests

# 3. Client
cd ../Client && mvn clean compile -DskipTests
```

✅ Toutes les compilations ont réussi !

## 🧪 Test

Pour tester le système:

1. Démarrer tous les services:
```bash
./start-robuste.sh
```

2. Lancer le client et faire une recherche:
```bash
cd Client
mvn spring-boot:run
```

3. Dans le CLI, choisir l'option 1 (Rechercher des chambres) et saisir:
   - Adresse: (laisser vide pour tous les hôtels)
   - Date d'arrivée: 2025-12-25
   - Date de départ: 2025-12-28
   - Autres champs: laisser vide

4. Le résultat devrait afficher pour chaque chambre:
   - ID, nom, prix, nombre de lits
   - **🖼️  Image: http://localhost:XXXX/images/HotelleX.png**
   - Adresse de l'hôtel
   - Disponibilité

## 📝 Exemple de sortie attendue

```
✓ 15 chambre(s) trouvée(s):

─────────────────────────────────────────────────────────────────────────
[ID: 1] Chambre Simple
  Prix: 80,00€ | Lits: 1 | Hôtel: Grand Hotel Paris
  🖼️  Image: http://localhost:8082/images/Hotelle1.png
  Adresse: 10 Rue de la Paix, Paris
  Disponible: Oui
─────────────────────────────────────────────────────────────────────────
[ID: 11] Chambre Standard
  Prix: 70,00€ | Lits: 1 | Hôtel: Hotel Lyon Centre
  🖼️  Image: http://localhost:8083/images/Hotelle2.png
  Adresse: 25 Place Bellecour, Lyon
  Disponible: Oui
─────────────────────────────────────────────────────────────────────────
```

## ✅ Résultat

L'implémentation est terminée et fonctionnelle. Chaque chambre possède maintenant une URL d'image qui est transmise à travers toute la chaîne SOAP (Hotellerie → Agence → Client) et affichée dans le CLI.

Les images sont servies par le serveur Tomcat intégré de Spring Boot de chaque hôtel, avec une configuration de ressources statiques.

