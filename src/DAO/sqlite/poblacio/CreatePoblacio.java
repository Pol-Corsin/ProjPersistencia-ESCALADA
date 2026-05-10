package DAO.sqlite.poblacio;

import model.Poblacio;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePoblacio {
    
    public void execute(Poblacio poblacio) throws SQLException {
        String sql = "INSERT INTO Poblacio (nom) VALUES (?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, poblacio.getNom());
            
            pstmt.executeUpdate();
            
            // Obtenir l'ID generat
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    poblacio.setId((int) generatedKeys.getLong(1));
                }
            }
        }
    }
}
