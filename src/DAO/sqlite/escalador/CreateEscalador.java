package DAO.sqlite.escalador;

import model.Escalador;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateEscalador {
    public void execute(Escalador escalador) throws SQLException {
        String sql = "INSERT INTO Escalador (alias, nom, edat, estil_pref) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, escalador.getAlias());
            pstmt.setString(2, escalador.getNom());
            pstmt.setInt(3, escalador.getEdat());
            pstmt.setString(4, escalador.getEstilPref());
            pstmt.executeUpdate();

            // Obtenir l'ID generat
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    escalador.setId((int) generatedKeys.getLong(1));
                }
            }
        }
    }
}