package APIJava.handlers;

import APIJava.ApiConfig;
import ServerRMI.ServiceDistant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static APIJava.main.Main.sendOptionResponse;


/**
 * Handler permettant de gérer l'accès à la route /travaux de l'API
 */
public class GetAccidentsHandler implements HttpHandler {

    private ServiceDistant serv;

    public GetAccidentsHandler(ServiceDistant serv) {
        this.serv = serv;
    }

    /**
     * Méthode permettant d'envoyer au client la liste des travaux de nancy
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("requete reçue");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendOptionResponse(exchange);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {

            System.out.println("Appel de getAccident");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            String json = null;
            System.out.println("Avant");
            json = serv.getReponseAPI(ApiConfig.ACCIDENTS_URL);
            //                System.out.println("résultat"+json);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode incidents = root.get("incidents");
            String res = incidents.toString();

            exchange.sendResponseHeaders(200, res.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }
}
