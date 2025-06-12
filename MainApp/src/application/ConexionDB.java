
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
        private static final String URL = "jdbc:sqlserver://DESKTOP-RSEQD08:1433;databaseName=UniversidadDB;encrypt=true;trustServerCertificate=true";
        private static final String USER = "sa"; // tu usuario
        private static final String PASSWORD = "JOS3sit0"; // tu contraseña real


    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
