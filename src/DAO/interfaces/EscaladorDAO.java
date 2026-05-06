package DAO.interfaces;

import model.Escalador;
import java.util.List;

public interface EscaladorDAO {
    // CRUD
    void create(Escalador escalador);
    void update(Escalador escalador);
    void delete(int id);
    
    // Busquedas
    Escalador findById(int id);
    Escalador findByAlias(String alias);
    List<Escalador> findAll();
}