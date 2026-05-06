package DAO.sqlite;

import DAO.interfaces.SectorDAO;
import DAO.sqlite.sector.*;
import model.Sector;

import java.sql.SQLException;
import java.util.List;

public class SQLiteSectorDAO implements SectorDAO {

    @Override
    public void create(Sector sector, int escolaId) {
        try {
            new CreateSector().execute(sector, escolaId);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_SECTOR: " + e.getMessage());
        }
    }

    @Override
    public void update(Sector sector) {
        new UpdateSector().execute(sector);
    }

    @Override
    public void delete(int id) {
        new DeleteSector().execute(id);
    }

    @Override
    public Sector findById(int id) {
        return new FindSector().byId(id);
    }

    @Override
    public Sector findByNomAndEscolaId(String nom, int escolaId) {
        return new FindSector().byNomAndEscolaId(nom, escolaId);
    }

    @Override
    public List<Sector> findAll() {
        return new FindSector().all();
    }

    @Override
    public List<Sector> findByEscolaId(int escolaId) {
        return new FindSector().byEscolaId(escolaId);
    }

    @Override
    public List<Sector> findAmbMesDeXVies(int x) {
        return new FindSector().ambMesDeXVies(x);
    }

    @Override
    public List<Sector> findByTipusVia(String tipusVia) {
        return new FindSector().byTipusVia(tipusVia);
    }
}