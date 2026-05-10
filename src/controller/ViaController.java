package controller;

import java.util.Scanner;

import DAO.interfaces.EscaladorDAO;
import DAO.interfaces.SectorDAO;
import DAO.interfaces.ViaDAO;
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
}
