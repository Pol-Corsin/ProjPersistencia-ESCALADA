package DAO.sqlite.escalador;

import model.Escalador;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateEscalador {
    public void execute(Escalador escalador) throws SQLException { // Lanzamos la excepción hacia arriba
        String sql = "INSERT INTO Escalador (alias, nom, edat, estil_pref) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, escalador.getAlias());
            pstmt.setString(2, escalador.getNom());
            pstmt.setInt(3, escalador.getEdat());
            pstmt.setString(4, escalador.getEstilPref());
            pstmt.executeUpdate();

        } // No hacemos catch aquí para que el Controller sepa qué pasó
    }
}