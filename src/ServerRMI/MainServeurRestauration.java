package ServerRMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServeurRestauration {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);

        ServiceRestauration serveur = new ServeurRestauration();

        ServiceRestauration objetExport= (ServiceRestauration) UnicastRemoteObject.exportObject(serveur,0);

        registry.bind("serviceBD",objetExport);
    }
}
