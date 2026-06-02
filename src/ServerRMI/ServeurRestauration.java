package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ServeurRestauration implements ServiceRestauration {
    protected ServeurRestauration() throws RemoteException {
    }

    @Override
    public void reservationRestaurant(Reservation reservation) throws RemoteException{

    }

    @Override
    public List<Restaurant> getRestaurants() throws RemoteException{
        Repository r = Repository.getInstance();
        try{
            return r.getRestaurants();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
