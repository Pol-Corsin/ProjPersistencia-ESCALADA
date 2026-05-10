package DAO.sqlite.via;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteLlarg {
    
    public void execute(int id) {
        String sql = "DELETE FROM Llarg WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ELIMINAR_LLARG: " + e.getMessage());
        }
    }
}