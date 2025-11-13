package org.tp1.agence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tp1.agence.dto.ChambreDTO;
import org.tp1.agence.dto.RechercheRequest;
import org.tp1.agence.dto.ReservationRequest;
import org.tp1.agence.dto.ReservationResponse;
import org.tp1.agence.service.AgenceService;

import java.util.List;

/**
 * Contrôleur REST qui expose les API pour le client
 * Le client enverra des requêtes HTTP REST à ce contrôleur
 */
@RestController
@RequestMapping("/api/agence")
@CrossOrigin(origins = "*")
public class AgenceController {

    @Autowired
    private AgenceService agenceService;

    /**
     * Endpoint pour rechercher des chambres
     * POST /api/agence/rechercher
     */
    @PostMapping("/rechercher")
    public ResponseEntity<List<ChambreDTO>> rechercherChambres(@RequestBody RechercheRequest request) {
        try {
            List<ChambreDTO> chambres = agenceService.rechercherChambres(request);
            return ResponseEntity.ok(chambres);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint pour effectuer une réservation
     * POST /api/agence/reserver
     */
    @PostMapping("/reserver")
    public ResponseEntity<ReservationResponse> effectuerReservation(@RequestBody ReservationRequest request) {
        try {
            ReservationResponse response = agenceService.effectuerReservation(request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            ReservationResponse errorResponse = new ReservationResponse(
                0,
                "Erreur serveur: " + e.getMessage(),
                false
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Endpoint de test pour vérifier que l'agence fonctionne
     * GET /api/agence/ping
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Agence SOAP opérationnelle");
    }
}

