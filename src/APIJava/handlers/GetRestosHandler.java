package APIJava.handlers;

import ServerRMI.ServiceRestauration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import donnees.Restaurant;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static APIJava.main.Main.sendOptionResponse;

public class GetRestosHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendOptionResponse(exchange);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
//                System.out.println(json);
            System.out.println("Appel de getRestos");
            Registry reg = LocateRegistry.getRegistry("10.82.149.19", 1099);
//                System.out.println(reg);
            ServiceRestauration resto = null;
            try {
                resto = (ServiceRestauration) reg.lookup("serviceBD");
                System.out.println("ref distante récupérée");
            } catch (NotBoundException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
            List<Restaurant> listeRestos = resto.getRestaurants();
//                    System.out.println(listeRestos.toString());
            String json = (new ObjectMapper()).writeValueAsString(listeRestos);
            System.out.println("Affichage JSON");
            System.out.println(json);
            System.out.println("FIn affichage JSON");
            exchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }

    }
}
