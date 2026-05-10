package DAO.sqlite.via;

import model.Llarg;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindLlarg {
    
    public Llarg byId(int id) {
        String sql = "SELECT * FROM Llarg WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToLlarg(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_LLARG: " + e.getMessage());
        }
        return null;
    }
    
    public List<Llarg> byViaId(int viaId) {
        String sql = "SELECT * FROM Llarg WHERE via_id = ? ORDER BY numero_llarg";
        List<Llarg> llargs = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, viaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                llargs.add(mapResultSetToLlarg(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_LLARGS_VIA: " + e.getMessage());
        }
        return llargs;
    }
    
    private Llarg mapResultSetToLlarg(ResultSet rs) throws SQLException {
        Llarg llarg = new Llarg(
            rs.getInt("via_id"),
            rs.getInt("numero_llarg"),
            rs.getDouble("llargada"),
            rs.getString("grau")
        );
        try {
            java.lang.reflect.Field idField = Llarg.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(llarg, rs.getInt("id"));
        } catch (Exception e) {
            // Si no podemos setear el ID, no pasa nada
        }
        return llarg;
    }
}