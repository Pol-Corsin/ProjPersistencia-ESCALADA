package model;

public class Poblacio {
    // DADES
    private int id;
    private String nom;

    // Constructor buit
    public Poblacio() {}
    
    public Poblacio(String nom) {
        this.nom = nom;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Poblacio{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
