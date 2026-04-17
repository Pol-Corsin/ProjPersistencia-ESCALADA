package model;

public class Sector {
    private String nom;
    private Double latitud;
    private Double longitud;
    private String restriccions;

    public Sector(String nom, Double latitud, Double longitud, String restriccions){
        this.nom = nom;
        this.latitud = latitud;
        this.longitud = longitud;
        this.restriccions = restriccions;
    }

    // GETTERS SETTERS

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

    // Restriccions
    public String getRestriccions() {
        return restriccions;
    }

    public void setRestriccions(String restriccions) {
        this.restriccions = restriccions;
    }
}
