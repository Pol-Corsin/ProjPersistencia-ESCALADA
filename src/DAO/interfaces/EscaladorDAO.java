package DAO.interfaces;

import model.Escalador;
import java.util.List;

public interface EscaladorDAO {
    // Operaciones CRUD básicas
    void create(Escalador escalador);
    void update(Escalador escalador);
    void delete(int id);
    
    // Búsquedas
    Escalador findById(int id);
    Escalador findByAlias(String alias);
    List<Escalador> findAll();
}