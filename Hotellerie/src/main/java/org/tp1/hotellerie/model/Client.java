package org.tp1.hotellerie.model;

public class Client {
    private String nom;
    private String prenom;
    private String numeroCarteBleue;

    public Client(String nom, String prenom, String numeroCarteBleue) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroCarteBleue = numeroCarteBleue;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroCarteBleue() {
        return numeroCarteBleue;
    }
}
