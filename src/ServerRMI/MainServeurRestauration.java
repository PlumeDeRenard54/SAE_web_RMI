package ServerRMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServeurRestauration {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.out.println("Demarrage du serveur ...");

        Registry registry = LocateRegistry.createRegistry(1099);
        System.out.println("Local registry créée");

        ServeurDistant serveur = new ServeurDistant();

        ServiceDistant objetExport= (ServiceDistant) UnicastRemoteObject.exportObject(serveur,0);

        registry.bind("serviceBD",objetExport);
        System.out.println("Service Disponible !");
    }
}