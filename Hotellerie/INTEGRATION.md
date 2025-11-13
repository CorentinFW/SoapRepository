# Guide d'Intégration Agence ↔ Hotellerie

## Étape 1 : Démarrer le service Hotellerie

```bash
cd /home/etudiant/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```

Vérifier que le WSDL est accessible :
```bash
curl http://localhost:8082/ws/hotel.wsdl
```

## Étape 2 : Copier le WSDL dans l'Agence

```bash
# Télécharger le WSDL
curl http://localhost:8082/ws/hotel.wsdl > /home/etudiant/Bureau/SoapRepository/Agence/src/main/resources/wsdl/hotel.wsdl
```

## Étape 3 : Recompiler l'Agence

Le plugin maven-jaxb2-plugin va générer automatiquement les classes Java à partir du WSDL.

```bash
cd /home/etudiant/Bureau/SoapRepository/Agence
mvn clean compile
```

Les classes seront générées dans `target/generated-sources/xjc/`

## Étape 4 : Mettre à jour HotelSoapClient dans l'Agence

Remplacer le contenu de `Agence/src/main/java/org/tp1/agence/client/HotelSoapClient.java` par l'implémentation réelle utilisant les classes générées.

## Étape 5 : Créer la configuration du client SOAP dans l'Agence

Ajouter une classe de configuration pour le client SOAP :

```java
@Configuration
public class SoapClientConfig {
    
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tp1.agence.wsdl.hotel");
        return marshaller;
    }
    
    @Bean
    public HotelSoapClient hotelSoapClient(Jaxb2Marshaller marshaller) {
        HotelSoapClient client = new HotelSoapClient();
        client.setDefaultUri("http://localhost:8082/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
```

## Étape 6 : Implémenter les appels SOAP réels

Dans `HotelSoapClient.java`, utiliser `WebServiceTemplate` pour faire les appels :

```java
public class HotelSoapClient extends WebServiceGatewaySupport {
    
    public List<ChambreDTO> rechercherChambres(RechercheRequest request) {
        // Créer la requête SOAP
        RechercherChambresRequest soapRequest = new RechercherChambresRequest();
        soapRequest.setAdresse(request.getAdresse());
        soapRequest.setDateArrive(request.getDateArrive());
        // ...
        
        // Appeler le service
        RechercherChambresResponse response = 
            (RechercherChambresResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8082/ws", soapRequest);
        
        // Convertir en DTO
        List<ChambreDTO> chambres = new ArrayList<>();
        for (Chambre soapChambre : response.getChambres()) {
            chambres.add(new ChambreDTO(
                soapChambre.getId(),
                soapChambre.getNom(),
                soapChambre.getPrix(),
                soapChambre.getNbrDeLit(),
                response.getHotelNom(),
                response.getHotelAdresse()
            ));
        }
        return chambres;
    }
}
```

## Étape 7 : Tester le flux complet

### Terminal 1 : Démarrer Hotellerie
```bash
cd /home/etudiant/Bureau/SoapRepository/Hotellerie
mvn spring-boot:run
```

### Terminal 2 : Démarrer Agence
```bash
cd /home/etudiant/Bureau/SoapRepository/Agence
mvn spring-boot:run
```

### Terminal 3 : Tester
```bash
# Test recherche via l'Agence
curl -X POST http://localhost:8081/api/agence/rechercher \
  -H "Content-Type: application/json" \
  -d '{
    "adresse": "Paris",
    "dateArrive": "2025-12-01",
    "dateDepart": "2025-12-05",
    "prixMin": 0,
    "prixMax": 200,
    "nbrEtoile": 5,
    "nbrLits": 2
  }'
```

## Architecture Finale

```
┌─────────────┐         ┌─────────────┐         ┌──────────────┐
│   Client    │  REST   │   Agence    │   SOAP  │  Hotellerie  │
│             │ ──────> │             │ ──────> │              │
│  (à créer)  │         │  Port 8081  │         │  Port 8082   │
│             │ <────── │             │ <────── │              │
└─────────────┘         └─────────────┘         └──────────────┘
                           ✅ REST                ✅ SOAP
```

## Fichiers à Créer/Modifier dans l'Agence

1. `src/main/resources/wsdl/hotel.wsdl` (copier depuis Hotellerie)
2. `src/main/java/org/tp1/agence/config/SoapClientConfig.java` (nouveau)
3. `src/main/java/org/tp1/agence/client/HotelSoapClient.java` (modifier)

## Vérifications

- [ ] Hotellerie démarre sur port 8082
- [ ] WSDL accessible : http://localhost:8082/ws/hotel.wsdl
- [ ] WSDL copié dans Agence/src/main/resources/wsdl/
- [ ] Agence recompilée (classes générées)
- [ ] Client SOAP configuré dans l'Agence
- [ ] Agence démarre sur port 8081
- [ ] Test REST → Agence → SOAP → Hotellerie fonctionne

## Commandes Utiles

```bash
# Vérifier les ports utilisés
lsof -i :8081
lsof -i :8082

# Télécharger le WSDL
wget http://localhost:8082/ws/hotel.wsdl -O hotel.wsdl

# Voir les classes générées
ls -la Agence/target/generated-sources/xjc/

# Logs en temps réel
tail -f Hotellerie/logs/app.log
tail -f Agence/logs/app.log
```

## Prochaine Étape

Créer le **Client REST** qui utilisera l'API de l'Agence.

