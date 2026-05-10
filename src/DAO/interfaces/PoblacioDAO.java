package DAO.interfaces;

import model.Poblacio;
import java.util.List;

public interface PoblacioDAO {
    void create(Poblacio poblacio);
    void update(Poblacio poblacio);
    void delete(int id);
    Poblacio findById(int id);
    List<Poblacio> findAll();
    Poblacio findByNom(String nom);
}
