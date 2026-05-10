package DAO.sqlite.assoliment;

import model.Assoliment;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FindAssoliment {
    
    public Assoliment byId(int id) {
        String sql = "SELECT * FROM Assoliments WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToAssoliment(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_ASSOLIMENT_PER_ID: " + e.getMessage());
        }
        return null;
    }
    
    public List<Assoliment> byEscaladorAlias(String alias) {
        String sql = "SELECT * FROM Assoliments WHERE escalador_alias = ?";
        List<Assoliment> assoliments = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, alias);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    assoliments.add(mapToAssoliment(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_ASSOLIMENTS_PER_ESCALADOR: " + e.getMessage());
        }
        return assoliments;
    }
    
    public List<Assoliment> byViaId(int viaId) {
        String sql = "SELECT * FROM Assoliments WHERE via_id = ?";
        List<Assoliment> assoliments = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, viaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    assoliments.add(mapToAssoliment(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_ASSOLIMENTS_PER_VIA: " + e.getMessage());
        }
        return assoliments;
    }
    
    public List<Assoliment> all() {
        String sql = "SELECT * FROM Assoliments";
        List<Assoliment> assoliments = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                assoliments.add(mapToAssoliment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_TOTS_ASSOLIMENTS: " + e.getMessage());
        }
        return assoliments;
    }
    
    private Assoliment mapToAssoliment(ResultSet rs) throws SQLException {
        String escaladorAlias = rs.getString("escalador_alias");
        int viaId = rs.getInt("via_id");
        String dataCompletatStr = rs.getString("data_completat");
        LocalDate dataCompletat = dataCompletatStr != null ? LocalDate.parse(dataCompletatStr) : null;
        String grauAssolit = rs.getString("grau_assolit");
        
        return new Assoliment(escaladorAlias, viaId, dataCompletat, grauAssolit);
    }
}
