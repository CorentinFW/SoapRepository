package org.tp1.hotellerie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hotel {
    private String nom;
    private String adresse;
    private int nbrEtoile;

    private final List<Chambre> chambres = new ArrayList<>(); // 0..n
    private final List<Reservation> reservations = new ArrayList<>(); // 0..n

    public Hotel() {}

    public Hotel(String nom, String adresse, int nbrEtoile) {
        this.nom = nom;
        this.adresse = adresse;
        this.nbrEtoile = nbrEtoile;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbrEtoile() {
        return nbrEtoile;
    }

    public void setNbrEtoile(int nbrEtoile) {
        this.nbrEtoile = nbrEtoile;
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addChambre(Chambre chambre) {
        if (chambre != null && !chambres.contains(chambre)) {
            chambres.add(chambre);
            chambre.setHotel(this);
        }
    }

    // Operations per UML
    public void reservation(Client c, Chambre chambre, String dateArrive, String dateDepart) {
        if (c == null || chambre == null) return;
        Reservation r = new Reservation(c, chambre, dateArrive, dateDepart);
        reservations.add(r);
        c.addReservation(r);
        chambre.addReservation(r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel hotel)) return false;
        return Objects.equals(nom, hotel.nom) && Objects.equals(adresse, hotel.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, adresse);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", nbrEtoile=" + nbrEtoile +
                ", chambres=" + chambres.size() +
                ", reservations=" + reservations.size() +
                '}';
    }
}