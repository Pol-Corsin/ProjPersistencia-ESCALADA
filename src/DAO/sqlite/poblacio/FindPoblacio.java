package DAO.sqlite.poblacio;

import model.Poblacio;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindPoblacio {
    
    public Poblacio byId(int id) {
        String sql = "SELECT * FROM Poblacio WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToPoblacio(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_POBLACIO_PER_ID: " + e.getMessage());
        }
        return null;
    }
    
    public Poblacio byNom(String nom) {
        String sql = "SELECT * FROM Poblacio WHERE nom = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToPoblacio(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_POBLACIO_PER_NOM: " + e.getMessage());
        }
        return null;
    }
    
    public List<Poblacio> all() {
        String sql = "SELECT * FROM Poblacio";
        List<Poblacio> poblacions = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                poblacions.add(mapToPoblacio(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CERCAR_TOTES_POBLACIONS: " + e.getMessage());
        }
        return poblacions;
    }
    
    private Poblacio mapToPoblacio(ResultSet rs) throws SQLException {
        Poblacio poblacio = new Poblacio();
        poblacio.setId(rs.getInt("id"));
        poblacio.setNom(rs.getString("nom"));
        return poblacio;
    }
}
