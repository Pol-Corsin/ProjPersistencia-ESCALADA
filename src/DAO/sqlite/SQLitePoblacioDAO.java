package DAO.sqlite;

import DAO.interfaces.PoblacioDAO;
import DAO.sqlite.poblacio.*;
import model.Poblacio;

import java.sql.SQLException;
import java.util.List;

public class SQLitePoblacioDAO implements PoblacioDAO {

    @Override
    public void create(Poblacio poblacio) {
        try {
            new CreatePoblacio().execute(poblacio);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_POBLACIO: " + e.getMessage());
        }
    }

    @Override
    public void update(Poblacio poblacio) {
        new UpdatePoblacio().execute(poblacio);
    }

    @Override
    public void delete(int id) {
        new DeletePoblacio().execute(id);
    }

    @Override
    public Poblacio findById(int id) {
        return new FindPoblacio().byId(id);
    }

    @Override
    public List<Poblacio> findAll() {
        return new FindPoblacio().all();
    }

    @Override
    public Poblacio findByNom(String nom) {
        return new FindPoblacio().byNom(nom);
    }
}
