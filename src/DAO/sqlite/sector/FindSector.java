package DAO.sqlite.sector;

import model.Sector;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindSector {
    
    public Sector byId(int id) {
        String sql = "SELECT * FROM Sector WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSector(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_SECTOR: " + e.getMessage());
        }
        return null;
    }
    
    public Sector byNomAndEscolaId(String nom, int escolaId) {
        String sql = "SELECT * FROM Sector WHERE nom = ? AND escola_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            pstmt.setInt(2, escolaId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSector(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_SECTOR_NOM: " + e.getMessage());
        }
        return null;
    }
    
    public List<Sector> all() {
        String sql = "SELECT * FROM Sector ORDER BY nom";
        List<Sector> sectors = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                sectors.add(mapResultSetToSector(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_LISTAR_SECTORS: " + e.getMessage());
        }
        return sectors;
    }
    
    public List<Sector> byEscolaId(int escolaId) {
        String sql = "SELECT * FROM Sector WHERE escola_id = ? ORDER BY nom";
        List<Sector> sectors = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, escolaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sectors.add(mapResultSetToSector(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_SECTORS_ESCOLA: " + e.getMessage());
        }
        return sectors;
    }
    
    public List<Sector> ambMesDeXVies(int x) {
        String sql = "SELECT s.*, COUNT(v.id) as num_vies FROM Sector s " +
                     "JOIN Via v ON s.id = v.sector_id AND v.estat = 'Apte' " +
                     "GROUP BY s.id HAVING num_vies > ? ORDER BY num_vies DESC";
        List<Sector> sectors = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, x);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sectors.add(mapResultSetToSector(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_SECTORS_VIES: " + e.getMessage());
        }
        return sectors;
    }
    
    public List<Sector> byTipusVia(String tipusVia) {
        // Busca sectors que només tenen vies del tipus especificat
        // Si tipusVia = "gel", només sectors de gel
        // Si tipusVia = "esportiva" o "clàssica", sectors que tenen vies esportives/clàssiques
        String sql = "SELECT s.* FROM Sector s " +
                     "JOIN Via v ON s.id = v.sector_id " +
                     "WHERE v.tipus = ? " +
                     "GROUP BY s.id ORDER BY s.nom";
        List<Sector> sectors = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipusVia);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sectors.add(mapResultSetToSector(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_SECTORS_TIPUS: " + e.getMessage());
        }
        return sectors;
    }
    
    private Sector mapResultSetToSector(ResultSet rs) throws SQLException {
        Integer latitud = null;
        Integer longitud = null;
        
        if (rs.getObject("latitud") != null) {
            latitud = rs.getInt("latitud");
        }
        if (rs.getObject("longitud") != null) {
            longitud = rs.getInt("longitud");
        }
        
        Sector sector = new Sector(
            rs.getString("nom"),
            latitud,
            longitud,
            rs.getString("restriccions")
        );
        
        // Setear campos adicionales
        try {
            java.lang.reflect.Field aproximacioField = Sector.class.getDeclaredField("aproximacio");
            aproximacioField.setAccessible(true);
            aproximacioField.set(sector, rs.getString("aproximacio"));
        } catch (Exception e) { }
        
        try {
            java.lang.reflect.Field popularitatField = Sector.class.getDeclaredField("popularitat");
            popularitatField.setAccessible(true);
            popularitatField.set(sector, rs.getString("popularitat"));
        } catch (Exception e) { }
        
        try {
            java.lang.reflect.Field idField = Sector.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(sector, rs.getInt("id"));
        } catch (Exception e) { }
        
        try {
            java.lang.reflect.Field escolaIdField = Sector.class.getDeclaredField("escolaId");
            escolaIdField.setAccessible(true);
            escolaIdField.set(sector, rs.getInt("escola_id"));
        } catch (Exception e) { }
        
        return sector;
    }
}