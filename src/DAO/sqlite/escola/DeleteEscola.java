package DAO.sqlite.escola;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DeleteEscola {

    public void execute(int id){
        String sql = "DELTE FROM Escola WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(id, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_ELIMINAR_ESCOLA: " + e.getMessage());
        }
    }
}