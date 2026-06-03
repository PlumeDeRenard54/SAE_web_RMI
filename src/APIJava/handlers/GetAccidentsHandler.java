package APIJava.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static APIJava.main.Main.getInfosAPI;

public class GetAccidentsHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Appel de getAccident");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String json = null;
        try {
            json = getInfosAPI("https://carto.g-ny.eu/data/cifs/cifs_waze_v2.json");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
