package DAO.sqlite.escola;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Escola;
import utils.DBConnection;

public class FindEscola {
    
    public Escola byId(int id){
        String sql = "SELECT * FROM Escola WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ptsmt = conn.prepareStatement(sql)){
            
            ptsmt.setInt(1, id);
            ResultSet rs = ptsmt.executeQuery();

            if (rs.next()){
                return mapResultSetToEscola(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLA_ID");
        }

        return null;
    }

    public Escola byNom(String nom){
        String sql = "SELECT * FROM Escola WHERE nom = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEscola(rs);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLA_NOM");
        }
        
        return null;
    }

    // funcio de mapeig
    private Escola mapResultSetToEscola(ResultSet rs) throws SQLException {
        Escola escola = new Escola(
                rs.getString("nom"),
                rs.getString("aproximacio"),
                rs.getString("popularitat"),
                rs.getString("restriccions"));
        escola.setId(rs.getInt("id"));
        return escola;
    }

}
