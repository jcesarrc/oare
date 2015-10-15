package co.gov.mme.oare.ws.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {


    public static Connection getConnection() {

        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://172.17.1.181:5432/subsidiosPruebas";
        String username = "postgres";
        String password = "postgres";

        Connection conn = null;

        try {

            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Ocurrio un error en obtener la conexion");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }


}