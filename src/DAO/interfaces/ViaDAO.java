package DAO.interfaces;

import model.Via;
import model.Llarg;
import java.util.List;

public interface ViaDAO {
    // Operaciones CRUD básicas
    void create(Via via);

    void update(Via via);

    void delete(int id);

    // Búsquedas
    Via findById(int id);

    List<Via> findAll();

    List<Via> findBySectorId(int sectorId);

    List<Via> findByEstat(String estat);

    List<Via> findByGrau(String grauMin, String grauMax);

    List<Via> findByEscolaId(int escolaId);

    List<Via> findRecentsApte(int dies);

    List<Via> findMesLlargues(int escolaId, int limit);

    // Métodos para Llarg
    void createLlarg(Llarg llarg);

    void updateLlarg(Llarg llarg);

    void deleteLlarg(int id);

    List<Llarg> findLlargsByViaId(int viaId);
}
