package org.tp1.hotellerie.model;

public class Chambre {
    private int id;
    private String nom;
    private float prix;
    private int nbrDeLit;

    public Chambre(int id, String nom, float prix, int nbrDeLit) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public float getPrix() {
        return prix;
    }

    public int getNbrDeLit() {
        return nbrDeLit;
    }
}
