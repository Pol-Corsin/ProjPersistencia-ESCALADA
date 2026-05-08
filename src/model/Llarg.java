package model;

public class Llarg {
    private int id;
    private int viaId;
    private int numeroLlarg;
    private double llargada;
    private String Grau;

    // Constructor buit
    public Llarg() {
    }

    public Llarg(int viaId, int numeroLlarg, double llargada, String grau) {
        this.viaId = viaId;
        this.numeroLlarg = numeroLlarg;
        this.llargada = llargada;
        this.Grau = grau;
    }

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Id de la via
    public int getViaId() {
        return viaId;
    }

    public void setViaId(int viaId) {
        this.viaId = viaId;
    }

    // Cuants llargs te la via
    public int getNumeroLlarg() {
        return numeroLlarg;
    }

    public void setNumeroLlarg(int numeroLlarg) {
        this.numeroLlarg = numeroLlarg;
    }

    // Llargada 
    public double getLlargada() {
        return llargada;
    }

    public void setLlargada(double llargada) {
        this.llargada = llargada;
    }

    // grau de dificultad de la via
    public String getGrau() {
        return Grau;
    }

    public void setGrau(String Grau) {
        this.Grau = Grau;
    }

}
