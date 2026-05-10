package DAO.sqlite.via;

import model.Via;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindVia {
    
    public Via byId(int id) {
        String sql = "SELECT * FROM Via WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToVia(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA: " + e.getMessage());
        }
        return null;
    }
    
    public List<Via> all() {
        String sql = "SELECT * FROM Via ORDER BY nom";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_LISTAR_VIES: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> bySectorId(int sectorId) {
        String sql = "SELECT * FROM Via WHERE sector_id = ? ORDER BY nom";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sectorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_SECTOR: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> byEstat(String estat) {
        String sql = "SELECT * FROM Via WHERE estat = ? ORDER BY nom";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estat);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_ESTAT: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> byGrau(String grauMin, String grauMax) {
        // Esta consulta requiere comparar grados - se implementa con una subconsulta
        String sql = "SELECT v.* FROM Via v " +
                     "JOIN Llarg l ON v.id = l.via_id " +
                     "WHERE l.grau >= ? AND l.grau <= ? " +
                     "GROUP BY v.id ORDER BY MIN(l.grau)";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, grauMin);
            pstmt.setString(2, grauMax);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_GRAU: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> byEscolaId(int escolaId) {
        String sql = "SELECT v.* FROM Via v " +
                     "JOIN Sector s ON v.sector_id = s.id " +
                     "WHERE s.escola_id = ? ORDER BY v.nom";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, escolaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_ESCOLA: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> recentsApte(int dies) {
        String sql = "SELECT v.* FROM Via v " +
                     "WHERE v.estat = 'Apte' AND v.data_reobertura >= date('now', '-' || ? || ' days') " +
                     "ORDER BY v.data_reobertura DESC";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, dies);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_RECENT: " + e.getMessage());
        }
        return vies;
    }
    
    public List<Via> mesLlargues(int escolaId, int limit) {
        String sql = "SELECT v.*, MAX(l.llargada) as max_llargada FROM Via v " +
                     "JOIN Sector s ON v.sector_id = s.id " +
                     "JOIN Llarg l ON v.id = l.via_id " +
                     "WHERE s.escola_id = ? AND v.estat = 'Apte' " +
                     "GROUP BY v.id ORDER BY max_llargada DESC LIMIT ?";
        List<Via> vies = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, escolaId);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vies.add(mapResultSetToVia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_VIA_LLARGUES: " + e.getMessage());
        }
        return vies;
    }
    
    private Via mapResultSetToVia(ResultSet rs) throws SQLException {
        Via via = new Via(
            rs.getInt("sector_id"),
            rs.getInt("creador_id"),
            rs.getString("nom"),
            rs.getString("tipus"),
            rs.getString("estat"),
            rs.getString("data_reobertura") != null ? 
                java.time.LocalDate.parse(rs.getString("data_reobertura")) : null,
            rs.getString("roca"),
            rs.getString("ancoratge"),
            rs.getString("orientacio"),
            rs.getString("restriccions")
        );
        // El ID no está en el constructor, lo setejamos después
        try {
            java.lang.reflect.Field idField = Via.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(via, rs.getInt("id"));
        } catch (Exception e) {
            // Si no podemos setear el ID, no pasa nada
        }
        return via;
    }
}