package DAO.sqlite.escalador;

import model.Escalador;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEscalador {

    public void execute(Escalador escalador) {
        String sql = "UPDATE Escalador SET alias = ?, nom = ?, edat = ?, estil_pref = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, escalador.getAlias());
            pstmt.setString(2, escalador.getNom());
            pstmt.setInt(3, escalador.getEdat());
            pstmt.setString(4, escalador.getEstilPref());
            pstmt.setInt(5, escalador.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_MODIFICAR_ESCALADOR: " + e.getMessage());
        }
    }
}
