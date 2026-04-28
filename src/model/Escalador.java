package model;

public class Escalador {
    // ! DADES
    private String alias;
    private String nom;
    private int edat;
    private String estilPref;

    // ! CONSTRUCTOR (Corregido: quitamos el parámetro sobrante)
    public Escalador(String alias, String nom, int edat, String estilPref) {
        this.alias = alias;
        this.nom = nom;
        this.edat = edat;
        this.estilPref = estilPref;
    }

    // ! GETTERS & SETTERS
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getEstilPref() {
        return estilPref;
    }

    public void setEstilPref(String estilPref) {
        this.estilPref = estilPref;
    }
}