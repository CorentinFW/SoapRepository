package org.tp1.agence.dto;

public class ChambreDTO {
    private int id;
    private String nom;
    private float prix;
    private int nbrDeLit;
    private String hotelNom;
    private String hotelAdresse;
    private String imageUrl;

    public ChambreDTO() {
    }

    public ChambreDTO(int id, String nom, float prix, int nbrDeLit, String hotelNom, String hotelAdresse) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
        this.hotelNom = hotelNom;
        this.hotelAdresse = hotelAdresse;
    }

    public ChambreDTO(int id, String nom, float prix, int nbrDeLit, String hotelNom, String hotelAdresse, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.nbrDeLit = nbrDeLit;
        this.hotelNom = hotelNom;
        this.hotelAdresse = hotelAdresse;
        this.imageUrl = imageUrl;
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

    public String getHotelNom() {
        return hotelNom;
    }

    public void setHotelNom(String hotelNom) {
        this.hotelNom = hotelNom;
    }

    public String getHotelAdresse() {
        return hotelAdresse;
    }

    public void setHotelAdresse(String hotelAdresse) {
        this.hotelAdresse = hotelAdresse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

