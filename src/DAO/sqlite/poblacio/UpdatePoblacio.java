package DAO.sqlite.poblacio;

import model.Poblacio;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePoblacio {
    
    public void execute(Poblacio poblacio) {
        String sql = "UPDATE Poblacio SET nom = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, poblacio.getNom());
            pstmt.setInt(2, poblacio.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ACTUALITZAR_POBLACIO: " + e.getMessage());
        }
    }
}
