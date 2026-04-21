package model;

public class Llarg {
    private int viaId;
    private int numeroLlarg;
    private int llargada;
    private String grau;
    
    public Llarg(int viaId, int numeroLlarg, int llargada, String grau) {
        this.viaId = viaId;
        this.numeroLlarg = numeroLlarg;
        this.llargada = llargada;
        this.grau = grau;
    }

    // Id de la via
    public int getViaId() {
        return viaId;
    }

    public void setViaId(int viaId) {
        this.viaId = viaId;
    }
    
    //Cuants llargs te la via
    public int getNumeroLlarg() {
        return numeroLlarg;
    }

    public void setNumeroLlarg(int numeroLlarg) {
        this.numeroLlarg = numeroLlarg;
    }

    // Llargada de la via
    public int getLlargada() {
        return llargada;
    }

    public void setLlargada(int llargada) {
        this.llargada = llargada;
    }

    // El grau de dificultad de la via
    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    
}
