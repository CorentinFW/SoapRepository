package org.tp1.hotellerie.model;

import java.util.Date;

public class Reservation {
    private int id;
    private Client client;
    private Chambre chambre;
    private Date dateArrive;
    private Date dateDepart;

    public Reservation(int id, Client client, Chambre chambre, Date dateArrive, Date dateDepart) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
    }

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
