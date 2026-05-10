package DAO.sqlite.via;

import model.Via;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateVia {
    
    public void execute(Via via) {
        String sql = "UPDATE Via SET sector_id = ?, creador_id = ?, nom = ?, tipus = ?, estat = ?, " +
                     "data_reobertura = ?, roca = ?, ancoratge = ?, orientacio = ?, restriccions = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, via.getSectorId());
            pstmt.setInt(2, via.getCreadorId());
            pstmt.setString(3, via.getNom());
            pstmt.setString(4, via.getTipus());
            pstmt.setString(5, via.getEstat());
            
            if (via.getDataReobertura() != null) {
                pstmt.setString(6, via.getDataReobertura().toString());
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }
            
            pstmt.setString(7, via.getRoca());
            pstmt.setString(8, via.getAncoratge());
            pstmt.setString(9, via.getOrientacio());
            pstmt.setString(10, via.getRestriccions());
            pstmt.setInt(11, via.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_MODIFICAR_VIA: " + e.getMessage());
        }
    }
}