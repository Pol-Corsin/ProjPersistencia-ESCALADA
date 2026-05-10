package DAO.sqlite.escola;

import model.Escola;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateEscola {
    public void execute(Escola escola) throws SQLException {
        String sql = "INSERT INTO Escola (nom, aproximacio, popularitat, restriccions) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ptsmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ptsmt.setString(1, escola.getNom());
            ptsmt.setString(2, escola.getAproximacio());
            ptsmt.setString(3, escola.getPopularitat());
            ptsmt.setString(4, escola.getRestriccions());
            ptsmt.executeUpdate();

            //Obtener l'ID generat
            try (ResultSet generatedKeys = ptsmt.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    escola.setId((int) generatedKeys.getLong(1));
                }
            }
        }
    }
}
