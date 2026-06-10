package APIJava.main;

import ServerRMI.ServiceDistant;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import APIJava.handlers.GetAccidentsHandler;
import APIJava.handlers.GetRestosHandler;
import APIJava.handlers.GetVelibsHandler;
import APIJava.handlers.ReservationHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * Classe Main permettant de lancer l'API Java
 */
public class Main {


    public static void main(String[] args) throws IOException {

        Properties config = new Properties();
        config.load(new FileInputStream("data/api_properties/config.properties"));

        Registry reg = LocateRegistry.getRegistry(config.getProperty("rmi.host"), 1099);
//                System.out.println(reg);
        ServiceDistant resto = null;
        try {
            resto = (ServiceDistant) reg.lookup("serviceBD");
            System.out.println("ref distante récupérée");
        } catch (NotBoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println(config.getProperty("api.port"));
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0",Integer.parseInt(config.getProperty("api.port"))), 0);

        //gestion de l'adresse http://localhost:8080/showVelib
        server.createContext("/velib", new GetVelibsHandler(resto));

        //reservation d'une table au resto
        server.createContext("/reserver", new ReservationHandler(resto, config.getProperty("rmi.host"), config.getProperty("rmi.port")));

        //récupération des restos
        server.createContext("/getRestos", new GetRestosHandler(resto, config.getProperty("rmi.host"), config.getProperty("rmi.port")));

        //récupérer les travaux
        server.createContext("/travaux", new GetAccidentsHandler(resto));

        //gestion d'un simple sout sur un clic, sera remplacé par le rmi j'imagine
        server.createContext("/soutMessage", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                System.out.println("Appel RMI");
                exchange.sendResponseHeaders(200, 0);
                exchange.getResponseBody().close();

            }
        });

        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());

        server.start();
        System.out.println("Serveur lancé sur 8888");

    }


    /**
     * Méthode permettant de passer outre les problème de CORS, notamment pour la requête de pre vérification des navigateurs
     * @param exchange l'objet représentant la réponse/requete http
     * @throws IOException
     */
    public static void sendOptionResponse(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }


}