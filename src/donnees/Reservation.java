package donnees;

public class Reservation {
    private int id;
    private String nomCli;
    private int idRestaurant;
    private String dateReservation;
    private int nbTables;

    public Reservation(String nomCli, int idRestaurant, String dateReservation, int nbTables) {
        this.nomCli = nomCli;
        this.idRestaurant = idRestaurant;
        this.dateReservation = dateReservation;
        this.nbTables = nbTables;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNomCli() {
        return nomCli;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public int getNbTables() {
        return nbTables;
    }
}
