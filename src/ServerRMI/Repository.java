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


        /*
        Test save res
         */
        // repo.saveRestaurant(new Restaurant(nomResto, adresse, latitude, longitude));

//        List<Restaurant> restaurants = repo.getRestaurants();
//        ServeurRestauration serveurRestauration = new ServeurRestauration();
//        Reservation reservation = new Reservation("Carnet", "Alexander", "0708965634", (restaurants.get(0)).getId(), "10-10-2010", 2);
//        serveurRestauration.reserverRestaurant(reservation);

//        for (int i=0; i<19;i++){
//            List<Restaurant> restaurants = repo.getRestaurants();
//            ServeurRestauration serveurRestauration = new ServeurRestauration();
//            Reservation reservation = new Reservation("Carnet", "Alexander", "0708965634", (restaurants.get(0)).getId(), "10-10-2010 10:05:00", 2);
//            serveurRestauration.reserverRestaurant(reservation);
//        }
        List<Restaurant> restaurants = repo.getRestaurants();
        ServeurRestauration serveurRestauration = new ServeurRestauration();
        Reservation reservation = new Reservation("Carnet", "Alexander", "0708965634", 9, "22-03-0022 22:22:00", 3);
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

    /**
     * récupère le plus grand id de restaurant et lui ajout 1 pour avoir la clé primaire suivante
     * @return un entier qui correspond au prochain id de restaurant
     * @throws SQLException
     */
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

    /**
     * Récupère le plus gros id de reservation et lui ajoute 1
     * @return le plus gros id de reservation et lui ajoute 1
     * @throws SQLException
     */
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

    /**
     *
     * @param restaurant
     * @throws SQLException
     */
    public void saveRestaurant(Restaurant restaurant) throws SQLException {
        int id = getNewIdRestaurant();
        restaurant.setId(id);

        Connection connect = DatabaseConnection.getConnection();

        String SQLPrep = "INSERT INTO e85555u.restaurant (id, nom, adresse, lat, lon, nbplaces) values (?,?,?,?,?,?)";

        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, restaurant.getId());
        prep.setString(2,restaurant.getNom());
        prep.setString(3,restaurant.getAdresse());
        prep.setDouble(4,restaurant.getLat());
        prep.setDouble(5,restaurant.getLon());
        prep.setInt(6,restaurant.getNbPlaces());

        prep.executeUpdate();
    }

    public void saveReservation(Reservation reservation) throws SQLException {
        int id = getNewIdReservation();
        reservation.setId(id);

        Connection connect = DatabaseConnection.getConnection();

        String SQLPrep = "INSERT INTO e85555u.reservation (idres, nomcli, prenomcli, numtel, idrestaurant, dateres, nbconvives) values (?,?,?,?,?, TO_DATE(?, 'DD-MM-YYYY HH24:MI:SS'),?)";

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
            Restaurant nouvRestaurant = new Restaurant(rs.getString("nom"), rs.getString("adresse"), rs.getDouble("lat"), rs.getDouble("lon"), rs.getInt("nbtables"));
            nouvRestaurant.setId(rs.getInt("id"));
            restaurants.add(nouvRestaurant);
        }
        return restaurants;
    }

    public boolean getReservationPossible(Reservation reservation) throws SQLException {
        Connection connect = DatabaseConnection.getConnection();

        // Récupérer nbTables

        String sqlTables = "SELECT nbTables FROM e85555u.restaurant WHERE id = ?";

        PreparedStatement prepTables = connect.prepareStatement(sqlTables);

        prepTables.setInt(1, reservation.getIdRestaurant());
        ResultSet rsTables = prepTables.executeQuery();
        int nbTablesTotal = 0;
        if(rsTables.next()) {
            nbTablesTotal = rsTables.getInt("nbTables");
        }

        System.out.println("nbTablesTotal: " + nbTablesTotal);

        // Récupérer disponibilités

        String sql =  "Select count(*) as nbReservations, NbConvives " +
                "from e85555u.reservation where IdRestaurant = ? AND DateRes BETWEEN TO_DATE(?, 'DD-MM-YYYY HH24:MI:SS') - INTERVAL '2' HOUR " +
                "AND TO_DATE(?, 'DD-MM-YYYY HH24:MI:SS') + INTERVAL '2' HOUR group by NbConvives";
        PreparedStatement prep = connect.prepareStatement(sql);
        prep.setInt(1, reservation.getIdRestaurant());
        prep.setString(2, reservation.getDateReservation());
        prep.setString(3, reservation.getDateReservation());
        ResultSet rs = prep.executeQuery();

        int nbTablesOccupees = 0;

        while(rs.next()) {
            int nbConvives = rs.getInt("NbConvives");
            int nbReservations = rs.getInt("nbReservations");
            nbTablesOccupees += (int) (Math.ceil(nbConvives/2.)*nbReservations);
        }
        System.out.println("nbTablesOccupees : " + nbTablesOccupees);
        System.out.println("nbTablesMax du resto : " +  nbTablesTotal);
        System.out.println("nbTablesVouluesParReservation : " + Math.ceil(reservation.getNbConvives()/2.));
        return (nbTablesTotal - nbTablesOccupees) * 2 > reservation.getNbConvives();
    }
}
