package APIJava.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import APIJava.main.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static APIJava.main.Main.sendOptionResponse;

public class GetVelibsHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendOptionResponse(exchange);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            System.out.println("Appel de getVelib");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            String jsonAdresse = null;
            String jsonCapa = null;
            try {
                jsonAdresse = Main.getInfosAPI("https://api.cyclocity.fr/contracts/nancy/gbfs/v2/station_information.json");
                jsonCapa = Main.getInfosAPI("https://api.cyclocity.fr/contracts/nancy/gbfs/v2/station_status.json");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            String res = getJsonVelib(jsonAdresse, jsonCapa);
//                System.out.println(res);

            exchange.sendResponseHeaders(200, res.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }


    public static String getJsonVelib(String jsonAdresse, String jsonCapa) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootAdresse = mapper.readTree(jsonAdresse);
        JsonNode stationsAdresse = rootAdresse.get("data").get("stations");

        JsonNode rootCapa = mapper.readTree(jsonCapa);
        JsonNode stationsCapa = rootCapa.get("data").get("stations");

        Map<String, ObjectNode> stationMap = new HashMap<>();

        for (JsonNode node : stationsAdresse) {
            String stationId = node.get("station_id").asText();
            // On cast en ObjectNode pour pouvoir le modifier/enrichir plus tard
            stationMap.put(stationId, (ObjectNode) node);
        }

        for (JsonNode nodeCapa : stationsCapa) {
            String stationId = nodeCapa.get("station_id").asText();

            // On vérifie si la station existe bien dans notre première liste
            if (stationMap.containsKey(stationId)) {
                ObjectNode stationAEnrichir = stationMap.get(stationId);

                // On fusionne tous les champs de l'API Capa dans l'API Adresse
                // (sauf le station_id qui y est déjà)
                nodeCapa.fields().forEachRemaining(entry -> {
                    if (!entry.getKey().equals("station_id")) {
                        stationAEnrichir.set(entry.getKey(), entry.getValue());
                    }
                });
            }
        }

        ArrayNode stationsFusionnees = mapper.createArrayNode();
        stationsFusionnees.addAll(stationMap.values());

        String jsonFinal = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stationsFusionnees);
//        System.out.println(jsonFinal);
        return jsonFinal;
    }

}
