package org.tp1.hotellerie.model;

import java.util.Objects;

public class Reservation {
    private String dateArrive;
    private String dateDepart;

    // Associations
    private Client client; // 1
    private Chambre chambre; // 1

    public Reservation() {}

    public Reservation(Client client, Chambre chambre, String dateArrive, String dateDepart) {
        this.client = client;
        this.chambre = chambre;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(dateArrive, that.dateArrive) && Objects.equals(dateDepart, that.dateDepart) &&
                Objects.equals(client, that.client) && Objects.equals(chambre, that.chambre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateArrive, dateDepart, client, chambre);
    }
}
