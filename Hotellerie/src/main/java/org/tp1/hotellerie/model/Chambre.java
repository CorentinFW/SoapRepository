package org.tp1.hotellerie.model;

public class Chambre {
    private int id;
    private String nom;
    private float prix;
    private int nbrDeLit;
    private String imageUrl;

    public Chambre(int id, String nom, float prix, int nbrDeLit) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
    }

    public Chambre(int id, String nom, float prix, int nbrDeLit, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
