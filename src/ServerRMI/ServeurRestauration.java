package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.io.Serializable;
import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

public class ServeurRestauration implements ServiceRestauration {
    @Override
    public void reserverRestaurant(Reservation reservation) {
        try {
            Repository.getInstance().saveReservation(reservation);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> getRestaurants() {
        Repository r = Repository.getInstance();
        try{
            return r.getRestaurants();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
