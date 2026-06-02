package donnees;

public class Restaurant {
    private int id;
    private String nom;
    private String adresse;
    private Double lat;
    private Double lon;

    public Restaurant(String nom, String adresse, Double lat, Double lon) {
        this.nom = nom;
        this.adresse = adresse;
        this.lat = lat;
        this.lon = lon;
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
}
