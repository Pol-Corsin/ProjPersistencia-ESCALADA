package DAO.sqlite;

import DAO.interfaces.EscolaDAO;
import DAO.sqlite.escola.*;
import model.Escola;

import java.sql.SQLException;
import java.util.List;

public class SQLiteEscolaDAO implements EscolaDAO {

    @Override
    public void create(Escola escola) {
        try {
            new CreateEscola().execute(escola);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CREAR_ESCOLA: " + e.getMessage());
        }
    }

    @Override
    public void update(Escola escola) {
        new UpdateEscola().execute(escola);
    }

    @Override
    public void delete(int id) {
        new DeleteEscola().execute(id);
    }

    @Override
    public Escola findById(int id) {
        return new FindEscola().byId(id);
    }

    @Override
    public Escola findByNom(String nom) {
        return new FindEscola().byNom(nom);
    }

    @Override
    public List<Escola> findAll() {
        return new FindEscola().all();
    }

    @Override
    public List<Escola> findAmbRestriccionsActives() {
        return new FindEscola().ambRestriccionsActives();
    }

    @Override
    public List<Escola> findAmbMesDeXVies(int x) {
        return new FindEscola().ambMesDeXVies(x);
    }

    @Override
    public void addPoblacio(int escolaId, String nomPoblacio) {
        new AddPoblacio().execute(escolaId, nomPoblacio);
    }

    @Override
    public List<String> findPoblacionsByEscolaId(int escolaId) {
        return new FindEscola().poblacionsByEscolaId(escolaId);
    }
}