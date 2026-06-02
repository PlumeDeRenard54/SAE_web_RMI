package ServerRMI;

import donnees.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Repository {

    public static void main(String[] args) throws SQLException {
        Repository repo = Repository.getInstance();
        repo.saveRestaurant(new Restaurant("RestoTresBo", "skibidi", 12113.,12131.));

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
        Connection connect = DatabaseConnection.getConnection();

        PreparedStatement prep = null;

        String SQLPrep = "INSERT INTO e85555u.restaurant (id, nom, adresse, lat, lon) values (?,?,?,?,?)";

        prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, getNewIdRestaurant());
        prep.setString(2,restaurant.getNom());
        prep.setString(3,restaurant.getAdresse());
        prep.setDouble(4,restaurant.getLat());
        prep.setDouble(5,restaurant.getLon());

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
            Restaurant NouvRestaurant = new Restaurant(rs.getString("nom"), rs.getString("adresse"), rs.getDouble("lat"), rs.getDouble("lon"));
            restaurants.add(NouvRestaurant);
        }
        return restaurants;
    }

    public void saveReservation(){

    }

    public void modifierReservation(){

    }

    public void modifierRestaurant(){

    }
}
