package DAO.sqlite.assoliment;

import model.Assoliment;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAssoliment {
    
    public void execute(Assoliment assoliment) {
        String sql = "UPDATE Assoliments SET grau_assolit = ?, data_completat = ? WHERE escalador_alias = ? AND via_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, assoliment.getGrauAssolit());
            pstmt.setString(2, assoliment.getDataCompletat() != null ? assoliment.getDataCompletat().toString() : null);
            pstmt.setString(3, assoliment.getEscaladorAlias());
            pstmt.setInt(4, assoliment.getViaId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ACTUALITZAR_ASSOLIMENT: " + e.getMessage());
        }
    }
}
