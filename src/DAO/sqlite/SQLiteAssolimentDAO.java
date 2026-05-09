package DAO.sqlite;

import DAO.interfaces.AssolimentDAO;
import DAO.sqlite.assoliment.*;
import model.Assoliment;

import java.sql.SQLException;
import java.util.List;

public class SQLiteAssolimentDAO implements AssolimentDAO {

    @Override
    public void create(Assoliment assoliment) {
        try {
            new CreateAssoliment().execute(assoliment);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_ASSOLIMENT: " + e.getMessage());
        }
    }

    @Override
    public void update(Assoliment assoliment) {
        new UpdateAssoliment().execute(assoliment);
    }

    @Override
    public void delete(int id) {
        new DeleteAssoliment().execute(id);
    }

    @Override
    public Assoliment findById(int id) {
        return new FindAssoliment().byId(id);
    }

    @Override
    public List<Assoliment> findAll() {
        return new FindAssoliment().all();
    }

    @Override
    public List<Assoliment> findByEscaladorAlias(String alias) {
        return new FindAssoliment().byEscaladorAlias(alias);
    }

    @Override
    public List<Assoliment> findByViaId(int viaId) {
        return new FindAssoliment().byViaId(viaId);
    }
}
