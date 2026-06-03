package ServerRMI;

import donnees.Reservation;
import donnees.Restaurant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceRestauration extends Remote {
    public boolean reserverRestaurant(Reservation reservation)throws RemoteException;
    public String getRestaurants()throws RemoteException;
}

