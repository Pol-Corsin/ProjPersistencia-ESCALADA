package DAO.interfaces;

import model.Escola;
import java.util.List;

public interface EscolaDAO {
    // Operaciones CRUD Basicas
    void create(Escola escola);
    void update(Escola escola);
    void delete(int id);

    // Busquedas
    Escola findById(int id);
    Escola findByName(String nom);
    List<Escola> findAll();

    // Metodes Extres
    void createPoblacio(int escolaId, String nomPoblacio);
    void findPoblacioByName(int escolaId, String nomPolbacio);
    void findPoblacioById(int escolaId, int poblacioId);
}
