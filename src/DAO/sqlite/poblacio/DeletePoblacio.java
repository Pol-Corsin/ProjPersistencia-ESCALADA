package DAO.sqlite.poblacio;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePoblacio {
    
    public void execute(int id) {
        String sql = "DELETE FROM Poblacio WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ELIMINAR_POBLACIO: " + e.getMessage());
        }
    }
}
