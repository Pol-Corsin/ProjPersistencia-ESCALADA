package model;

import java.time.LocalDate;

public class Via {
    private Integer sectorId;
    private String creadorAlias;
    private String nom;
    private String tipus;
    private String estat;
    private LocalDate dataReobertura;
    private String roca;
    private String ancoratge;
    private String orientacio;
    private String restriccions;
    
    public Via(Integer sectorId, String creadorAlias, String nom, String tipus, String estat, LocalDate dataReobertura,
        String roca, String ancoratge, String orientacio, String restriccions) {
        this.sectorId = sectorId;
        this.creadorAlias = creadorAlias;
        this.nom = nom;
        this.tipus = tipus;
        this.estat = estat;
        this.dataReobertura = dataReobertura;
        this.roca = roca;
        this.ancoratge = ancoratge;
        this.orientacio = orientacio;
        this.restriccions = restriccions;
    }

    // GETTERS SETTERS

    // Sector
    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    //Alias
    public String getCreadorAlias() {
        return creadorAlias;
    }

    public void setCreadorAlias(String creadorAlias) {
        this.creadorAlias = creadorAlias;
    }

    // Nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Tipus
    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    // Estat
    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    // Datareobertura
    public LocalDate getDataReobertura() {
        return dataReobertura;
    }

    public void setDataReobertura(LocalDate dataReobertura) {
        this.dataReobertura = dataReobertura;
    }

    //Roca
    public String getRoca() {
        return roca;
    }

    public void setRoca(String roca) {
        this.roca = roca;
    }

    // Ancoratge
    public String getAncoratge() {
        return ancoratge;
    }

    public void setAncoratge(String ancoratge) {
        this.ancoratge = ancoratge;
    }

    // Orinetacio
    public String getOrientacio() {
        return orientacio;
    }

    public void setOrientacio(String orientacio) {
        this.orientacio = orientacio;
    }

    // Restriccions
    public String getRestriccions() {
        return restriccions;
    }

    public void setRestriccions(String restriccions) {
        this.restriccions = restriccions;
    }

    
}
