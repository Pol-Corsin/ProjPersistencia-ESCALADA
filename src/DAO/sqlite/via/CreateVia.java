package DAO.sqlite.via;

import model.Via;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateVia {
    
    public void execute(Via via) throws SQLException {
        String sql = "INSERT INTO Via (sector_id, creador_id, nom, tipus, estat, data_reobertura, roca, ancoratge, orientacio, restriccions) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
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
            
            pstmt.executeUpdate();
            
            // Obtenir l'ID generat
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    via.setId((int) generatedKeys.getLong(1));
                }
            }
        }
    }
}