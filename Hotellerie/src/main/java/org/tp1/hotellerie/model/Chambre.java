package org.tp1.hotellerie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chambre {
    private int id;
    private String nom;
    private float prix;
    private int nbrDeLit;

    // Association
    private Hotel hotel; // 1
    private final List<Reservation> reservations = new ArrayList<>(); // 0..n

    public Chambre() {}

    public Chambre(int id, String nom, float prix, int nbrDeLit) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getNbrDeLit() {
        return nbrDeLit;
    }

    public void setNbrDeLit(int nbrDeLit) {
        this.nbrDeLit = nbrDeLit;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chambre chambre)) return false;
        return id == chambre.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", nbrDeLit=" + nbrDeLit +
                '}';
    }
}
