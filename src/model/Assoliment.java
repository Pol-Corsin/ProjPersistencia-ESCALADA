package model;

import java.time.LocalDate;

public class Assoliment {
    private String escaladorAlias;
    private int viaId;
    private LocalDate dataCompletat;
    private String grauAssolit;
    
    public Assoliment(String escaladorAlias, int viaId, LocalDate dataCompletat, String grauAssolit) {
        this.escaladorAlias = escaladorAlias;
        this.viaId = viaId;
        this.dataCompletat = dataCompletat;
        this.grauAssolit = grauAssolit;
    }

    public String getEscaladorAlias() {
        return escaladorAlias;
    }

    public void setEscaladorAlias(String escaladorAlias) {
        this.escaladorAlias = escaladorAlias;
    }

    public int getViaId() {
        return viaId;
    }

    public void setViaId(int viaId) {
        this.viaId = viaId;
    }

    public LocalDate getDataCompletat() {
        return dataCompletat;
    }

    public void setDataCompletat(LocalDate dataCompletat) {
        this.dataCompletat = dataCompletat;
    }

    public String getGrauAssolit() {
        return grauAssolit;
    }

    public void setGrauAssolit(String grauAssolit) {
        this.grauAssolit = grauAssolit;
    }

    
}
