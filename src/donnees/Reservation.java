package donnees;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class Reservation implements Serializable {
    private int id;
    private String nomCli;
    private String prenomCli;
    private String numTel;
    private int idRestaurant;
    private String dateReservation;
    private int nbConvives;

    public Reservation(String nomCli, String prenomCli, String numTel, int idRestaurant, String dateReservation, int nbConvives) {
        this.nomCli = nomCli;
        this.prenomCli = prenomCli;
        this.numTel = numTel;
        this.idRestaurant = idRestaurant;
        this.dateReservation = dateReservation;
        this.nbConvives = nbConvives;
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

    public String getPrenomCli() {
        return prenomCli;
    }

    public String getNumTel() {
        return numTel;
    }

    public int getNbConvives() {
        return nbConvives;
    }
}
