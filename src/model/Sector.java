package model;

public class Sector {
    private int id;
    private int escolaId;
    private String nom;
    private Double latitud;
    private Double longitud;
    private String aproximacio;
    private String popularitat;
    private String restriccions;

    // Constructor buit
    public Sector() {}
    
    public Sector(String nom, Double latitud, Double longitud, String restriccions){
        this.nom = nom;
        this.latitud = latitud;
        this.longitud = longitud;
        this.restriccions = restriccions;
    }

    // GETTERS SETTERS

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // Escola ID
    public int getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(int escolaId) {
        this.escolaId = escolaId;
    }
    
    // Nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Latitud
    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    // Longitud
    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    // Aproximacio
    public String getAproximacio() {
        return aproximacio;
    }

    public void setAproximacio(String aproximacio) {
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
