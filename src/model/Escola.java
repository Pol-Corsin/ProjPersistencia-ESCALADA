package model;

public class Escola {
    // DADES
    private String nom;
    private String aproximacio;
    private String popularitat;

    public Escola(String nom, String aproximacio, String popularitat){
        this.nom = nom;
        this.aproximacio = aproximacio;
        this.popularitat = popularitat;
    }

    // GETTERS SETTERS

    // Nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    // Aproximacio
    public String getAproximacio(){
        return aproximacio;
    }

    public void setAproximacio(String aproximacio){
        this.aproximacio = aproximacio;
    }

    // Popularitat
    public String getPopularitat() {
        return popularitat;
    }

    public void setPopularitat(String popularitat) {
        this.popularitat = popularitat;
    }
}
