package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // La ruta debe coincidir con la carpeta de tu proyecto (visto en tus capturas)
    private static final String URL = "jdbc:sqlite:testsdb/test_db.db";
    private static Connection instance = null;

    /**
     * Obtiene la conexión actual o crea una nueva si no existe.
     */
    public static Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                try {
                    Class.forName("org.sqlite.JDBC");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("SQLite JDBC driver no encontrado. Añade sqlite-jdbc al classpath.", e);
                }

                instance = DriverManager.getConnection(URL);

                // ACTIVAR LAS FOREIGN KEYS (Vital para SQLite)
                try (Statement stmt = instance.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
        return instance;
    }

    /**
     * Cierra la conexión si está abierta.
     */
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}