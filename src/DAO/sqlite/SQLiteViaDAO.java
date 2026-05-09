package DAO.sqlite;

import DAO.interfaces.ViaDAO;
import DAO.sqlite.via.*;
import model.Via;
import model.Llarg;

import java.sql.SQLException;
import java.util.List;

public class SQLiteViaDAO implements ViaDAO {

    @Override
    public void create(Via via) {
        try {
            new CreateVia().execute(via);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_VIA: " + e.getMessage());
        }
    }

    @Override
    public void update(Via via) {
        new UpdateVia().execute(via);
    }

    @Override
    public void delete(int id) {
        new DeleteVia().execute(id);
    }

    @Override
    public Via findById(int id) {
        return new FindVia().byId(id);
    }

    @Override
    public List<Via> findAll() {
        return new FindVia().all();
    }

    @Override
    public List<Via> findBySectorId(int sectorId) {
        return new FindVia().bySectorId(sectorId);
    }

    @Override
    public List<Via> findByEstat(String estat) {
        return new FindVia().byEstat(estat);
    }

    @Override
    public List<Via> findByGrau(String grauMin, String grauMax) {
        return new FindVia().byGrau(grauMin, grauMax);
    }

    @Override
    public List<Via> findByEscolaId(int escolaId) {
        return new FindVia().byEscolaId(escolaId);
    }

    @Override
    public List<Via> findRecentsApte(int dies) {
        return new FindVia().recentsApte(dies);
    }

    @Override
    public List<Via> findMesLlargues(int escolaId, int limit) {
        return new FindVia().mesLlargues(escolaId, limit);
    }

    @Override
    public void createLlarg(Llarg llarg) {
        try {
            new CreateLlarg().execute(llarg);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_LLARG: " + e.getMessage());
        }
    }

    @Override
    public void updateLlarg(Llarg llarg) {
        new UpdateLlarg().execute(llarg);
    }

    @Override
    public void deleteLlarg(int id) {
        new DeleteLlarg().execute(id);
    }

    @Override
    public List<Llarg> findLlargsByViaId(int viaId) {
        return new FindLlarg().byViaId(viaId);
    }
}