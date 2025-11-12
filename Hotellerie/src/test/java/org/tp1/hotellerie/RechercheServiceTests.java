package org.tp1.hotellerie;

import org.junit.jupiter.api.Test;
import org.tp1.hotellerie.model.Chambre;
import org.tp1.hotellerie.model.Client;
import org.tp1.hotellerie.model.Hotel;
import org.tp1.hotellerie.service.RechercheService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RechercheServiceTests {

    @Test
    void recherche_filtreEtDisponibilite() {
        RechercheService service = new RechercheService();

        Hotel h1 = new Hotel("Hotel A", "Paris 8e", 4);
        Chambre c11 = new Chambre(1, "101", 120f, 2);
        Chambre c12 = new Chambre(2, "102", 80f, 1);
        h1.addChambre(c11);
        h1.addChambre(c12);

        Hotel h2 = new Hotel("Hotel B", "Lyon Presqu'ile", 3);
        Chambre c21 = new Chambre(3, "201", 60f, 1);
        h2.addChambre(c21);

        service.addHotel(h1);
        service.addHotel(h2);

        // Une réservation occupe c11 du 2025-11-15 au 2025-11-18
        Client cl = new Client("Doe", "John", 1234);
        h1.reservation(cl, c11, "2025-11-15", "2025-11-18");

        // Recherche à Paris, étoile >= 4, prix entre 70 et 130, 2 personnes, dates qui chevauchent => c11 doit être exclue
        ArrayList<Chambre> res1 = service.rechercheHotel("paris", "2025-11-16", "2025-11-17", 130f, 70f, 4, 2);
        assertTrue(res1.isEmpty(), "La chambre 101 est occupée et ne doit pas apparaître");

        // Période qui n'intersecte pas -> c11 disponible
        ArrayList<Chambre> res2 = service.rechercheHotel("paris", "2025-11-18", "2025-11-20", 130f, 70f, 4, 2);
        assertEquals(1, res2.size());
        assertEquals(c11, res2.get(0));

        // Filtre adresse et étoiles: rechercher Lyon, étoiles min 3, prix max 80, 1 personne
        ArrayList<Chambre> res3 = service.rechercheHotel("lyon", "2025-12-01", "2025-12-05", 80f, 0f, 3, 1);
        assertEquals(1, res3.size());
        assertEquals(c21, res3.get(0));
    }
}
