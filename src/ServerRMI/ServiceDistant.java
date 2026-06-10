package ServerRMI;

import donnees.Reservation;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDistant extends Remote {
    public boolean reserverRestaurant(Reservation reservation)throws RemoteException;
    public String getRestaurants()throws RemoteException;
    public String getReponseAPI(String uri) throws IOException, InterruptedException;
}

