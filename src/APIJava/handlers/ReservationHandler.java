package APIJava.handlers;

import ServerRMI.ServiceDistant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import donnees.Reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.stream.Collectors;

import static APIJava.main.Main.sendOptionResponse;


/**
 * Handler permettant de gérer l'accès à la route /reserver de l'API
 */
public class ReservationHandler implements HttpHandler {

    private ServiceDistant serv;
    private final String rmiHost;
    private final String rmiPort;

    public ReservationHandler(ServiceDistant serv, String host, String port) {
        this.serv = serv;
        this.rmiHost = host;
        this.rmiPort = port;
    }

    /**
     * Méthode permettant de réserver une table dans un restaurant
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendOptionResponse(exchange);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

                // Convertir le flux en une seule String (ton JSON reçu)
                String body = reader.lines().collect(Collectors.joining("\n"));

                // Console log pour vérifier ce que le client t'envoie
                System.out.println("JSON reçu en POST : " + body);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(body);

                String nom = jsonNode.get("nom").asText();
                String prenom = jsonNode.get("prenom").asText();
                String date = jsonNode.get("date").asText();
                String heure = jsonNode.get("heure").asText();
                int numResto = jsonNode.get("idResto").asInt();
                int nbPersonnes = jsonNode.get("nbPersonnes").asInt();
                String numT = jsonNode.get("numT").asText();

                //modification de la date pour être conforme à la bd
                String seulementDate = date.split("T")[0];

                String[] parts = seulementDate.split("-");
                String annee = parts[0];
                String mois = parts[1];
                String jour = parts[2];

                String datePourSQL = jour + "-" + mois + "-" + annee + " " + heure + ":00";

                Reservation res = new Reservation(nom, prenom, numT, numResto, datePourSQL, nbPersonnes);

                Registry reg = LocateRegistry.getRegistry(rmiHost, Integer.parseInt(rmiPort));
//                        System.out.println(reg);
                ServiceDistant resto = null;
                try {
                    resto = (ServiceDistant) reg.lookup("serviceBD");
                    System.out.println("ref distante récupérée");
                } catch (NotBoundException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }

//                System.out.println(resto.getRestaurants());
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Content-Type", "application/json");

                boolean resOk = resto.reserverRestaurant(res);
                System.out.println("Reservation faite : " + resOk);

                String response;
                int codeStatus;

                if (resOk) {
                    codeStatus = 201;
                    response = "{\"status\": \"success\", \"message\": \"Réservation validée \"}";
                } else {
                    codeStatus = 400;
                    response = "{\"status\": \"error\", \"message\": \"Plus de places disponibles ou erreur serveur\"}";
                }

                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(codeStatus, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, -1);
                exchange.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            exchange.close();
        }
    }
}