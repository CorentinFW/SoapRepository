package org.tp1.hotellerie.model;

import java.util.Date;

public class Reservation {
    private int id;
    private Client client;
    private Chambre chambre;
    private Date dateArrive;
    private Date dateDepart;

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public Date getDateDepart() {
        return dateDepart;
    }
}
