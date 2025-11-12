package org.tp1.hotellerie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private String nom;
    private String prenom;
    private int carteDeCredit;

    // Associations
    private final List<Reservation> reservations = new ArrayList<>();

    public Client() {}

    public Client(String nom, String prenom, int carteDeCredit) {
        this.nom = nom;
        this.prenom = prenom;
        this.carteDeCredit = carteDeCredit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getCarteDeCredit() {
        return carteDeCredit;
    }

    public void setCarteDeCredit(int carteDeCredit) {
        this.carteDeCredit = carteDeCredit;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    // utilitaires de relation
    void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(nom, client.nom) && Objects.equals(prenom, client.prenom) && carteDeCredit == client.carteDeCredit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, carteDeCredit);
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", carteDeCredit=" + carteDeCredit +
                '}';
    }
}
