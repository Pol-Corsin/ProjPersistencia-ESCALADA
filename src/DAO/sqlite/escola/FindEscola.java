package DAO.sqlite.escola;

import model.Escola;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindEscola {

    public Escola byId(int id) {
        String sql = "SELECT * FROM Escola WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEscola(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLA: " + e.getMessage());
        }
        return null;
    }

    public Escola byNom(String nom) {
        String sql = "SELECT * FROM Escola WHERE nom = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEscola(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLA_NOM: " + e.getMessage());
        }
        return null;
    }

    public List<Escola> all() {
        String sql = "SELECT * FROM Escola ORDER BY nom";
        List<Escola> escoles = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                escoles.add(mapResultSetToEscola(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_LISTAR_ESCOLES: " + e.getMessage());
        }
        return escoles;
    }

    public List<Escola> ambRestriccionsActives() {
        // Busca escoles que tinguin restriccions actives (no buides)
        String sql = "SELECT * FROM Escola WHERE restriccions IS NOT NULL AND restriccions != '' ORDER BY nom";
        List<Escola> escoles = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                escoles.add(mapResultSetToEscola(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLES_RESTRICCIONS: " + e.getMessage());
        }
        return escoles;
    }

    public List<Escola> ambMesDeXVies(int x) {
        String sql = "SELECT e.*, COUNT(v.id) as num_vies FROM Escola e " +
                "JOIN Sector s ON e.id = s.escola_id " +
                "JOIN Via v ON s.id = v.sector_id AND v.estat = 'Apte' " +
                "GROUP BY e.id HAVING num_vies > ? ORDER BY num_vies DESC";
        List<Escola> escoles = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, x);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                escoles.add(mapResultSetToEscola(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_ESCOLES_VIES: " + e.getMessage());
        }
        return escoles;
    }

    public List<String> poblacionsByEscolaId(int escolaId) {
        String sql = "SELECT p.nom FROM Poblacio p " +
                "JOIN Escola_Poblacio ep ON p.id = ep.poblacio_id " +
                "WHERE ep.escola_id = ?";
        List<String> poblacions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, escolaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                poblacions.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_BUSCAR_POBLACIONS: " + e.getMessage());
        }
        return poblacions;
    }

    // funcio de mapeig
    private Escola mapResultSetToEscola(ResultSet rs) throws SQLException {
        Escola escola;
        try {
            escola = Escola.class.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // Si no tiene el campo, ignoramos
        }
        // Setear ID
        try {
            java.lang.reflect.Field field = Escola.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(escola, value);
        } catch (Exception e) {
            // Ignore if the field does not exist or cannot be set.
        }
    }

}
