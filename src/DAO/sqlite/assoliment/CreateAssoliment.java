package DAO.sqlite.assoliment;

import model.Assoliment;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class CreateAssoliment {

    public void execute(Assoliment assoliment) throws SQLException {
        String sql = "INSERT INTO Assoliments (escalador_alias, via_id, data_completat, grau_assolit) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, assoliment.getEscaladorAlias());
            pstmt.setInt(2, assoliment.getViaId());
            pstmt.setString(3, assoliment.getDataCompletat() != null ? assoliment.getDataCompletat().toString()
                    : LocalDate.now().toString());
            pstmt.setString(4, assoliment.getGrauAssolit());

            pstmt.executeUpdate();

            // Obtenir l'ID generat pero taula Assoliments pot no tenir ID primari que es
            // retorni
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Si la taula no retorna ID, aquesta part no executa
                }
            }
        }
    }
}
