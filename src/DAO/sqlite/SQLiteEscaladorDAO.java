package DAO.sqlite;

import DAO.interfaces.EscaladorDAO;
import DAO.sqlite.escalador.*; // Importa todas tus clases de acción
import model.Escalador;

import java.sql.SQLException;
import java.util.List;

public class SQLiteEscaladorDAO implements EscaladorDAO {

    @Override
    public void create(Escalador escalador) {
        try {
            new CreateEscalador().execute(escalador);
        } catch (SQLException e) {
            // Verificamos si el error es por violación de unicidad (Código 19 en SQLite)
            if (e.getErrorCode() == 19) {
                throw new RuntimeException("EL_ALIAS_YA_EXISTE");
            }
            throw new RuntimeException("ERROR_DESCONOCIDO");
        }
    }

    @Override
    public void update(Escalador escalador) {
        new UpdateEscalador().execute(escalador);
    }

    @Override
    public void delete(int id) {
        new DeleteEscalador().execute(id);
    }

    @Override
    public Escalador findById(int id) {
        return new FindEscalador().byId(id);
    }

    @Override
    public Escalador findByAlias(String alias) {
        return new FindEscalador().byAlias(alias);
    }

    @Override
    public List<Escalador> findAll() {
        return new FindEscalador().all();
    }
}