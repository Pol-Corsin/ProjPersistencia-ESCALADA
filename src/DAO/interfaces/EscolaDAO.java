package DAO.interfaces;

import model.Escola;
import java.util.List;

public interface EscolaDAO {
    // Operaciones CRUD básicas
    void create(Escola escola);
    void update(Escola escola);
    void delete(int id);
    
    // Búsquedas
    Escola findById(int id);
    Escola findByNom(String nom);
    List<Escola> findAll();
    List<Escola> findAmbRestriccionsActives();
    List<Escola> findAmbMesDeXVies(int x);
    
    // Métodos de relación
    void addPoblacio(int escolaId, String nomPoblacio);
    List<String> findPoblacionsByEscolaId(int escolaId);
}
