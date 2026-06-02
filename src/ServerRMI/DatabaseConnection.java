package ServerRMI;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection = null;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver Oracle introuvable !");
            e.printStackTrace();
        }
    }

    private DatabaseConnection() {}

    /**
     * Fournit l'unique connexion à la base de données.
     * Si elle n'existe pas ou est fermée, elle est créée à partir du fichier db.properties.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();

            try (FileInputStream fis = new FileInputStream("data/db_properties/db.properties")) {
                props.load(fis);
            } catch (IOException e) {
                throw new SQLException("Impossible de charger le fichier db.properties. Vérifiez son emplacement.", e);
            }

            String url = props.getProperty("db.url");;
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            if (user == null || pass == null) {
                throw new SQLException("Propriétés de connexion manquantes dans db.properties.");
            }

            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }

    /**
     * Méthode utilitaire pour fermer proprement la connexion globale en fin d'application.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connexion globale à la base de données fermée.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}