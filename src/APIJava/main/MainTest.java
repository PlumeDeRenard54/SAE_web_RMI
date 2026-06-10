//package APIJava.main;
//
//import APIJava.handlers.GetVelibsHandler;
//import com.sun.net.httpserver.HttpServer;
//
//import java.net.InetSocketAddress;
//
//public class MainTest {
//
//    public static void main(String[] args) {
//        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0",8888), 0);
//
//        //gestion de l'adresse http://localhost:8080/showVelib
//        server.createContext("/velib", new GetVelibsHandler());
//    }
//
//}
