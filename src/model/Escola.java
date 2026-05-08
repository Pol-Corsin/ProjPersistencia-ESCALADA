package model;

public class Escola {
    // DADES
    private int id;
    private String nom;
    private String aproximacio;
    private String popularitat;
    private String restriccions;

    // Constructor buit
    public Escola() {}
    
    public Escola(String nom, String aproximacio, String popularitat){
        this.nom = nom;
        this.aproximacio = aproximacio;
        this.popularitat = popularitat;
    }

    // GETTERS SETTERS

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
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
    
    // Restriccions
    public String getRestriccions() {
        return restriccions;
    }

    public void setRestriccions(String restriccions) {
        this.restriccions = restriccions;
    }
}
