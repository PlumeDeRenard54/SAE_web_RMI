package ServerRMI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import donnees.Reservation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ServeurDistant implements ServiceDistant {

    private static final HttpClient client = HttpClient.newBuilder()
            .proxy(ProxySelector.of(new InetSocketAddress("www-cache", 3128)))
            .executor(Executors.newFixedThreadPool(10))
            .build();


    protected ServeurDistant() throws RemoteException {
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
    public String getRestaurants() throws RemoteException{
        Repository r = Repository.getInstance();
        try{
            System.out.println("Recupération des restaurants");
            String s = (new ObjectMapper()).writeValueAsString(r.getRestaurants());
            System.out.println(s);
            return s;
        }catch(SQLException | JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getReponseAPI(String url) throws RemoteException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            System.out.println("code " + response.statusCode());
            return response.body();
        } catch (InterruptedException e) {
            throw new RemoteException("Erreur appel API", e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
