package donnees;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String nom;
    private String adresse;
    private Double lat;
    private Double lon;
    private int nbPlaces;

    public Restaurant(String nom, String adresse, Double lat, Double lon,  int nbPlaces) {
        this.nom = nom;
        this.adresse = adresse;
        this.lat = lat;
        this.lon = lon;
        this.nbPlaces =  nbPlaces;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }
}
