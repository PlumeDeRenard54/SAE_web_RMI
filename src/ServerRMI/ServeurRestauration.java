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
    public synchronized boolean reserverRestaurant(Reservation reservation) throws RemoteException{
        try {
            //verifier si assez de place disponible
            boolean reservationOK =  Repository.getInstance().getReservationPossible(reservation);
            System.out.println("Reservation possible : " + reservationOK);
            if(!reservationOK){
                return false;
            }
            //reservation
            Repository.getInstance().saveReservation(reservation);
            System.out.println("Reservation !");
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Restaurant> getRestaurants() throws RemoteException{
        Repository r = Repository.getInstance();
        try{
            System.out.println("Recupération des restaurants");
            return r.getRestaurants();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }



}
