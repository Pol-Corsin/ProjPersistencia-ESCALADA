package DAO.sqlite.escola;

import model.Escola;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEscola {
    
    public void execute(Escola escola) {
        String sql = "UPDATE Escola SET nom = ?, aproximacio = ?, popularitat = ?, restriccions = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, escola.getNom());
            pstmt.setString(2, escola.getAproximacio());
            pstmt.setString(3, escola.getPopularitat());
            pstmt.setString(4, escola.getRestriccions());
            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("ERROR_MODIFICAR_ESCOLA " + e.getMessage());
        }
    }
}
