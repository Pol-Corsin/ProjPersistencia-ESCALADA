package DAO.sqlite.via;

import model.Llarg;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateLlarg {
    
    public void execute(Llarg llarg) throws SQLException {
        String sql = "INSERT INTO Llarg (via_id, numero_llarg, llargada, grau) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, llarg.getViaId());
            pstmt.setInt(2, llarg.getNumeroLlarg());
            pstmt.setDouble(3, llarg.getLlargada());
            pstmt.setString(4, llarg.getGrau());
            
            pstmt.executeUpdate();
            
            // Obtenir l'ID generat
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    llarg.setId((int) generatedKeys.getLong(1));
                }
            }
        }
    }
}