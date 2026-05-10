package DAO.sqlite.via;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteVia {
    
    public void execute(int id) {
        String sql = "DELETE FROM Via WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ELIMINAR_VIA: " + e.getMessage());
        }
    }
}