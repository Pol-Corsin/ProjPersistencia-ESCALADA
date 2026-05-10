package DAO.interfaces;

import model.Assoliment;
import java.util.List;

public interface AssolimentDAO {
    void create(Assoliment assoliment);
    void update(Assoliment assoliment);
    void delete(int id);
    Assoliment findById(int id);
    List<Assoliment> findAll();
    List<Assoliment> findByEscaladorAlias(String alias);
    List<Assoliment> findByViaId(int viaId);
}
