package DAO.sqlite.via;

import model.Llarg;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateLlarg {
    
    public void execute(Llarg llarg) {
        String sql = "UPDATE Llarg SET via_id = ?, numero_llarg = ?, llargada = ?, grau = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, llarg.getViaId());
            pstmt.setInt(2, llarg.getNumeroLlarg());
            pstmt.setDouble(3, llarg.getLlargada());
            pstmt.setString(4, llarg.getGrau());
            pstmt.setInt(5, llarg.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_MODIFICAR_LLARG: " + e.getMessage());
        }
    }
}