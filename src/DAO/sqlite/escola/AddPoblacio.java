package DAO.sqlite.escola;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPoblacio {
    
    public void execute(int escolaId, String nomPoblacio) {
        try (Connection conn = DBConnection.getConnection()) {
            // Primero verificamos si la población existe
            int poblacioId = getOrCreatePoblacio(conn, nomPoblacio);
            
            // Luego verificamos si la relación ya existe
            String checkSql = "SELECT 1 FROM Escola_Poblacio WHERE escola_id = ? AND poblacio_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, escolaId);
                checkStmt.setInt(2, poblacioId);
                java.sql.ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Ya existe la relación
                    return;
                }
            }
            
            // Creamos la relación
            String insertSql = "INSERT INTO Escola_Poblacio (escola_id, poblacio_id) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, escolaId);
                insertStmt.setInt(2, poblacioId);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ADD_POBLACIO: " + e.getMessage());
        }
    }
    
    private int getOrCreatePoblacio(Connection conn, String nomPoblacio) throws SQLException {
        // Buscar población existente
        String selectSql = "SELECT id FROM Poblacio WHERE nom = ?";
try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, nomPoblacio);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        
        // Crear nueva población
        String insertSql = "INSERT INTO Poblacio (nom) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, nomPoblacio);
            pstmt.executeUpdate();
        }
        
        // Obtener el ID generado
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, nomPoblacio);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        
        throw new SQLException("No se pudo crear o encontrar la población");
    }
}
