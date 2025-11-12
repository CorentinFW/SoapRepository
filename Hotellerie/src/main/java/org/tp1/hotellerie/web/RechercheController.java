package org.tp1.hotellerie.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tp1.hotellerie.model.Chambre;
import org.tp1.hotellerie.service.RechercheService;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/recherche", produces = MediaType.APPLICATION_JSON_VALUE)
public class RechercheController {

    private final RechercheService service;

    public RechercheController(RechercheService service) {
        this.service = service;
    }

    @GetMapping
    public ArrayList<Chambre> recherche(
            @RequestParam(name = "adress", required = false) String adress,
            @RequestParam(name = "dateArrive") String dateArrive,
            @RequestParam(name = "dateDepart") String dateDepart,
            @RequestParam(name = "prixMax", defaultValue = "3.4028235E38") float prixMax,
            @RequestParam(name = "prixMin", defaultValue = "0") float prixMin,
            @RequestParam(name = "nbrEtoile", defaultValue = "0") int nbrEtoile,
            @RequestParam(name = "client", defaultValue = "0") int client
    ) {
        return service.rechercheHotel(adress, dateArrive, dateDepart, prixMax, prixMin, nbrEtoile, client);
    }
}

