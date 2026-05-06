package DAO.interfaces;

import model.Via;
import java.util.List;
public interface ViaDAO {
    // CRUD
    void create(Via via);
    void update(Via via);
    void delete(int id);

    // Busquedas
    Via findById(int id);
    Via findByNom(String nomVia);
    Via findByNomId(String nomVia, int id);
    List<Via> findAll();
    List<Via> findByPopularitat(String popularitat);
    List<Via> findByAncoratge(String ancoratge);
    List<Via> findByRoca(String roca);
    List<Via> findByEstat(String estat);
    List<Via> findByOrientacio(String orientacio);
    List<Via> findByTipus(String tipus);

    //Funciones Extras
    void createLlarg(int viaId, int llarg);
    void findByLlarg(int viaID, int llarg);
    

}
