package DAO.sqlite.escalador;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEscalador {
    
    public void execute(int id) {
        String sql = "DELETE FROM Escalador WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ELIMINAR_ESCALADOR: " + e.getMessage());
        }
    }
}
