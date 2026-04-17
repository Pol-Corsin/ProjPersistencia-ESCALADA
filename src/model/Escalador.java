package model;

public class Escalador {
    // ! DADES
    private String alias;
    private String nom;
    private int edat;
    private String estilPref;

    // ! CONSTRUCTOR
    public Escalador(String alias, String nom, int edat, String nivellMax, String estilPref) {
        this.alias = alias;
        this.nom = nom;
        this.edat = edat;
        this.estilPref = estilPref;
    }

    // ! GETTERS & SETTERS

    // ? ALIAS
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    // ? NOM
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    // ? EDAT
    public int getEdat() {
        return edat;
    }
    public void setEdat(int edat) {
        this.edat = edat;
    }

    // ? ESTIL PREFERIT
    public String getEstilPref() {

    }
    public void setEstilPref(String estilPref) {
        this.estilPref = estilPref;
    }
}


