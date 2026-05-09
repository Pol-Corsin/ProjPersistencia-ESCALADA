package DAO.sqlite.sector;

import model.Sector;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSector {
    
    public void execute(Sector sector) {
        String sql = "UPDATE Sector SET nom = ?, latitud = ?, longitud = ?, aproximacio = ?, popularitat = ?, restriccions = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, sector.getNom());
            
            if (sector.getLatitud() != null) {
                pstmt.setInt(2, sector.getLatitud());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            if (sector.getLongitud() != null) {
                pstmt.setInt(3, sector.getLongitud());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            pstmt.setString(4, sector.getAproximacio());
            pstmt.setString(5, sector.getPopularitat());
            pstmt.setString(6, sector.getRestriccions());
            pstmt.setInt(7, sector.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_MODIFICAR_SECTOR: " + e.getMessage());
        }
    }
}