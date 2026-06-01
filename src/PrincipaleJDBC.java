package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrincipaleJDBC {

    public static void main(String[] args) {

        PreparedStatement prep = null;
        ResultSet rs = null;

        try {
            Connection connect = DatabaseConnection.getConnection();
            System.out.println("Connexion réussie via le Singleton !");

            String SQLPrep = "SELECT * FROM e85555u.RESTAURANT";
            prep = connect.prepareStatement(SQLPrep);
            rs = prep.executeQuery();

            System.out.println("\n***** LISTE DES RESTAURANTS *****");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("NOM");
                String adresse = rs.getString("ADRESSE");

                System.out.println("-> [" + id + "] " + nom + " - " + adresse);
            }

        } catch (SQLException e) {
            System.out.println("*** ERREUR SQL ou de Configuration ***");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
                DatabaseConnection.closeConnection();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}