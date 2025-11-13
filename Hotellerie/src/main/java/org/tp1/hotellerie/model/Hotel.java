package org.tp1.hotellerie.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nom;
    private String adresse;
    private Type type;
    private List<Reservation> listeReservation = new ArrayList<>();
    private List<Chambre> listeDesChambres = new ArrayList<>();

    // Nouveau constructeur par défaut (conserve compatibilité)
    public Hotel() {
    }

    // Nouveau constructeur pratique
    public Hotel(String nom, String adresse, Type type) {
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Type getType() {
        return type;
    }

    public List<Reservation> getListeReservation() {
        return listeReservation;
    }

    public int getNombreReservations() {
        return listeReservation.size();
    }

    public int getNombreChambres() {
        return listeDesChambres.size();
    }

    public List<Chambre> getListeDesChambres() {
        return listeDesChambres;
    }

    public void ajoutReservation(Reservation reservation) {
        if (reservation != null) {
            this.listeReservation.add(reservation);
        }
    }

    public void ajoutChambre(Chambre chambre) {
        if (chambre != null) {
            this.listeDesChambres.add(chambre);
        }
    }

}
