package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Repository {

    public static void main(String[] args) throws SQLException, RemoteException {
        Repository repo = Repository.getInstance();
        /*
        Test save resto
         */
        repo.saveRestaurant(new Restaurant("RestoTresBo", "skibidi", 12113.,12131.));



        /*
        Test save res
         */

        List<Restaurant> restaurants = repo.getRestaurants();
        ServeurRestauration serveurRestauration = new ServeurRestauration();
        Reservation reservation = new Reservation("Carnet", "Alexander", "0708965634", (restaurants.get(0)).getId(), "10-10-2010", 2);
        serveurRestauration.reserverRestaurant(reservation);

        System.out.println(repo.getRestaurants());
    }

    private static Repository repo;

    private Repository() {
    }

    public synchronized static Repository getInstance() {
        if(repo == null) {
            repo = new Repository();
        }
        return repo;
    }

    private int getNewIdRestaurant() throws SQLException {
        Connection connect = DatabaseConnection.getConnection();

        String sql = "select max(ID) from e85555u.restaurant";

        PreparedStatement prep = connect.prepareStatement(sql);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            return rs.getInt(1)+1;
        }
        return 0;
    }

    private int getNewIdReservation() throws SQLException {
        Connection connect = DatabaseConnection.getConnection();

        String sql = "select max(idres) from e85555u.reservation";

        PreparedStatement prep = connect.prepareStatement(sql);
        ResultSet rs = prep.executeQuery();
        if(rs.next()) {
            return rs.getInt(1)+1;
        }
        return 0;
    }

    public void saveRestaurant(Restaurant restaurant) throws SQLException {
        int id = getNewIdRestaurant();
        restaurant.setId(id);

        Connection connect = DatabaseConnection.getConnection();

        String SQLPrep = "INSERT INTO e85555u.restaurant (id, nom, adresse, lat, lon) values (?,?,?,?,?)";

        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, restaurant.getId());
        prep.setString(2,restaurant.getNom());
        prep.setString(3,restaurant.getAdresse());
        prep.setDouble(4,restaurant.getLat());
        prep.setDouble(5,restaurant.getLon());

        prep.executeUpdate();
    }

    public void saveReservation(Reservation reservation) throws SQLException {
        int id = getNewIdReservation();
        reservation.setId(id);

        Connection connect = DatabaseConnection.getConnection();

        String SQLPrep = "INSERT INTO e85555u.reservation (idres, nomcli, prenomcli, numtel, idrestaurant, dateres, nbconvives) values (?,?,?,?,?, TO_DATE(?, 'DD-MM-YYYY'),?)";

        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, reservation.getId());
        prep.setString(2,reservation.getNomCli());
        prep.setString(3,reservation.getPrenomCli());
        prep.setString(4,reservation.getNumTel());
        prep.setInt(5,reservation.getIdRestaurant());
        prep.setString(6,reservation.getDateReservation());
        prep.setInt(7,reservation.getNbConvives());

        prep.executeUpdate();
    }

    public List<Restaurant> getRestaurants() throws SQLException {
        Connection connect = DatabaseConnection.getConnection();
        PreparedStatement prep = null;
        String SQL = "Select * from e85555u.restaurant";
        prep = connect.prepareStatement(SQL);
        ResultSet rs = prep.executeQuery();
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        while (rs.next()) {
            Restaurant nouvRestaurant = new Restaurant(rs.getString("nom"), rs.getString("adresse"), rs.getDouble("lat"), rs.getDouble("lon"));
            nouvRestaurant.setId(rs.getInt("id"));
            restaurants.add(nouvRestaurant);
        }
        return restaurants;
    }
}
