package DAO.sqlite.escalador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Escalador;
import utils.DBConnection;

public class FindEscalador {
    public Escalador byId(int id) {
        String sql = "SELECT * FROM Escalador WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEscalador(rs);

            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCALADOR_ID");
        }

        return null;
    }

    public List<Escalador> all() {
        String sql = "SELECT * FROM Escalador ORDER BY alias";
        List<Escalador> escaladors = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    escaladors.add(mapResultSetToEscalador(rs));
                }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_LISTAR_ESCALADORS");
        }

        return escaladors;
    }

    // funcio de mapeig
    private Escalador mapResultSetToEscalador(ResultSet rs) throws SQLException {
        Escalador escalador = new Escalador(
                rs.getString("alias"),
                rs.getString("nom"),
                rs.getInt("edat"),
                rs.getString("estil_pref"));
        escalador.setId(rs.getInt("id"));
        return escalador;
    }
}
