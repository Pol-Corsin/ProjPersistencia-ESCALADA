package controller;

import java.util.List;
import java.util.Scanner;

import DAO.interfaces.EscaladorDAO;
import DAO.interfaces.SectorDAO;
import DAO.interfaces.ViaDAO;
import model.Sector;
import view.MenuTerminal;

public class ViaController {
    private final ViaDAO viaDAO;
    private final SectorDAO sectorDAO;
    private final EscaladorDAO escaladorDAO;
    private final MenuTerminal view;
    private final Scanner sc;

    public ViaController(ViaDAO viaDAO, SectorDAO sectorDAO, EscaladorDAO escaladorDAO) {
        this.viaDAO = viaDAO;
        this.sectorDAO = sectorDAO;
        this.escaladorDAO = escaladorDAO;
        this.view = new MenuTerminal();
        this.sc = new Scanner(System.in);
    }

    public void crearVia() {
        view.missatge("\n=== CREAR VIA ===");

        // 1. Demanar escalador (creador)
        view.missatge("Alias de l'escalador creador:");
        String aliasCreador = sc.nextLine();
        model.Escalador escaladorCreador = escaladorDAO.findByAlias(aliasCreador);
        if (escaladorCreador == null) {
            view.mostrarError("L'escalador no existeix. Creeu-lo primer.");
            return;
        }

        // 2. Demanar sector
        view.missatge("ID del sector:");
        int sectorId = leerEntero();
        Sector sector = sectorDAO.findById(sectorId);
        if (sector == null) {
            view.mostrarError("El sector no existeix.");
            return;
        }

        // 3. Verificar tipus de via (ha de ser compatible amb el sector)
        List<String> tipusExistents = obtenirTipusViesSector(sectorId);

    }

    // ########################## FUNC AUXILIARS
    

    private List<String> obtenirTipusViesSector(int sectorId) {

        List<Via> viewSector = viaDAO.findBySectorId(sectorId)

        return null;
    }


    private int leerEntero() {
        while (true) {
            try {
                String input = sc.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                view.mostrarError("Introdueix un número vàlid:");
            }
        }
    }

    
}
