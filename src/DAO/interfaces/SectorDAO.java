package DAO.interfaces;

import model.Sector;
import java.util.List;

public interface SectorDAO {
    // Operaciones CRUD básicas
    void create(Sector sector, int escolaId);
    void update(Sector sector);
    void delete(int id);
    
    // Búsquedas
    Sector findById(int id);
    Sector findByNomAndEscolaId(String nom, int escolaId);
    List<Sector> findAll();
    List<Sector> findByEscolaId(int escolaId);
    List<Sector> findAmbMesDeXVies(int x);
    List<Sector> findByTipusVia(String tipusVia);
}
