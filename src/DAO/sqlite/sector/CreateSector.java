package DAO.sqlite.sector;

import model.Sector;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSector {

    public void execute(Sector sector, int escolaId) throws SQLException {
        String sql = "INSERT INTO Sector (escola_id, nom, coordenades, aproximacio, popularitat, restriccions) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, escolaId);
            pstmt.setString(2, sector.getNom());

            // Coordenades: latitud,longitud
            if (sector.getLatitud() != null && sector.getLongitud() != null) {
                pstmt.setString(3, sector.getLatitud() + "," + sector.getLongitud());
            } else {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }

            pstmt.setString(4, sector.getAproximacio());
            pstmt.setString(5, sector.getPopularitat());
            pstmt.setString(6, sector.getRestriccions());

            pstmt.executeUpdate();

            // Obtenir l'ID generat
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sector.setId((int) generatedKeys.getLong(1));
                    sector.setEscolaId(escolaId);
                }
            }
        }
    }
}