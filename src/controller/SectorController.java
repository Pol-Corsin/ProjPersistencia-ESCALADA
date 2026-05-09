package controller;

import java.util.List;
import java.util.Scanner;

import DAO.interfaces.EscolaDAO;
import DAO.interfaces.SectorDAO;
import model.Sector;
import view.MenuTerminal;

public class SectorController {

    private final SectorDAO sectorDAO;
    private final EscolaDAO escolaDAO;
    private final MenuTerminal view;
    private final Scanner sc;

    public SectorController(SectorDAO sectorDAO, EscolaDAO escolaDAO) {
        this.sectorDAO = sectorDAO;
        this.escolaDAO = escolaDAO;
        this.view = new MenuTerminal();
        this.sc = new Scanner(System.in);
    }

    public void crearSector() {
        view.missatge("\n=== CREAR SECTOR ===");

        // 1. Escolir escola
        view.missatge("Escoles disponibles:");
        List<model.Escola> escoles = escolaDAO.findAll();
        if (escoles.isEmpty()) {
            view.mostrarError("No hi ha escoles. Creeu una escola primer.");
            return;
        }

        for (model.Escola e : escoles) {
            view.missatge(e.getId() + ". " + e.getNom());
        }

        view.missatge("ID de l'escola:");
        int escolaId = leerEntero();
        model.Escola escola = escolaDAO.findById(escolaId);
        if (escola == null) {
            view.mostrarError("Escola no trobada.");
            return;
        }

        // 2. Nom del sector
        view.missatge("Nom del Sector:");
        String nom = sc.nextLine();

        // Verificar que no estigui ja en la escola
        if (sectorDAO.findByNomAndEscolaId(nom, escolaId) != null) {
            view.mostrarError("Ja existeix un sector amb aquest nom en aquesta escola.");
            return;
        }

        // 3. Coordenades
        view.missatge("Latitud (enter: ex 41) - enter per none:");
        String latStr = sc.nextLine();
        Integer latitud = null;
        if (!latStr.trim().isEmpty()) {
            try {
                latitud = Integer.parseInt(latStr);
            } catch (NumberFormatException e) {
                view.mostrarError("Latitud invàlida.");
            }
        }

        view.missatge("Longitud (enter: ex 1) - enter per none:");
        String lonStr = sc.nextLine();
        Integer longitud = null;
        if (!lonStr.trim().isEmpty()) {
            try {
                longitud = Integer.parseInt(lonStr);
            } catch (NumberFormatException e) {
                view.mostrarError("Longitud invàlida.");
            }
        }

        // 4. Aproximació
        view.missatge("Aproximació (com arribar):");
        String aproximacio = sc.nextLine();

        // 5. Popularitat
        String popularitat = elegirPopularitat();

        // 6. Restriccions (opcional)
        view.missatge("Restriccions (enter per saltar):");
        String restriccions = sc.nextLine();
        if (restriccions.trim().isEmpty()) {
            restriccions = null;
        }

        // Crear el sector
        Sector nouSector = new Sector();
        nouSector.setNom(nom);
        nouSector.setLatitud(latitud);
        nouSector.setLongitud(longitud);
        nouSector.setAproximacio(aproximacio);
        nouSector.setPopularitat(popularitat);
        nouSector.setRestriccions(restriccions);

        try {
            sectorDAO.create(nouSector, escolaId);
            view.mostrarExito("Sector creat correctament!");
        } catch (RuntimeException e) {
            view.mostrarError("Error en crear el sector: " + e.getMessage());
        }
    }

    public void modificarSector() {
        view.missatge("\n=== MODIFICAR SECTOR ===");
        List<Sector> sectors = sectorDAO.findAll();

        if (sectors.isEmpty()) {
            view.mostrarError("No hi ha sectors.");
            return;
        }

        view.missatge("Sectors disponibles:");
        for (Sector s : sectors) {
            view.missatge(s.getId() + ". " + s.getNom() + " (Escola id: " + s.getEscolaId() + ")");
        }

        view.missatge("Escriu l'ID del sector a modificar (0 per sortir):");
        int id = leerEntero();
        if (id == 0)
            return;

        Sector sector = sectorDAO.findById(id);
        if (sector == null) {
            view.mostrarError("Sector no trobat");
        }

        // Menú de modificació
        view.missatge("\nQuè voleu modificar?");
        view.missatge("1. Nom");
        view.missatge("2. Coordenades");
        view.missatge("3. Aproximació");
        view.missatge("4. Popularitat");
        view.missatge("5. Restriccions");
        view.missatge("0. Sortir");

        int opcio = leerEntero();

        switch (opcio) {
            case 1:
                view.missatge("Nou nom:");
                sector.setNom(sc.nextLine());
                break;
            case 2:
                view.missatge("Nova latitud:");
                String latStr = sc.nextLine();
                if (!latStr.trim().isEmpty()) {
                    sector.setLatitud(Integer.parseInt(latStr));
                }
                view.missatge("Nova longitud:");
                String lonStr = sc.nextLine();
                if (!lonStr.trim().isEmpty()) {
                    sector.setLongitud(Integer.parseInt(lonStr));
                }
                break;
            case 3:
                view.missatge("Nova aproximació:");
                sector.setAproximacio(sc.nextLine());
                break;
            case 4:
                sector.setPopularitat(elegirPopularitat());
                break;
            case 5:
                view.missatge("Noves restriccions:");
                sector.setRestriccions(sc.nextLine());
                break;
            default:
                return;
        }

        try {
            sectorDAO.update(sector);
            view.mostrarExito("Sector modificat.");
        } catch (RuntimeException e) {
            view.mostrarError("Error: " + e.getMessage());
        }
    }

    public void llistarSectors() {
        view.missatge("\n=== LLISTAR SECTORS ===");
        List<Sector> sectors = sectorDAO.findAll();

        if (sectors.isEmpty()) {
            view.missatge("No hi ha sectors.");
            return;
        }

        for (Sector s : sectors) {
            view.missatge("---");
            view.missatge("ID: " + s.getId());
            view.missatge("Nom: " + s.getNom());
            view.missatge("Escola ID: " + s.getEscolaId());

            if (s.getLatitud() != null && s.getLongitud() != null) {
                view.missatge("Coordenades: " + s.getLatitud() + ", " + s.getLongitud());
            }

            view.missatge("Aproximació: " + s.getAproximacio());
            view.missatge("Popularitat: " + s.getPopularitat());

            if (s.getRestriccions() != null && !s.getRestriccions().isEmpty()) {
                view.missatge("Restriccions: " + s.getRestriccions());
            }
        }
    }

    public void eliminarSector() {
        view.missatge("\n=== ELIMINAR SECTOR ===");
        List<Sector> sectors = sectorDAO.findAll();

        if (sectors.isEmpty()) {
            view.mostrarError("No hi ha sectors.");
            return;
        }

        view.missatge("Sectors disponibles:");
        for (Sector s : sectors) {
            view.missatge(s.getId() + ". " + s.getNom());
        }

        view.missatge("ID del sector a eliminar (0 per sortir):");
        int id = leerEntero();
        if (id == 0)
            return;

        view.missatge("Esteu segur? (s/n):");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("s")) {
            try {
                sectorDAO.delete(id);
                view.mostrarExito("Sector eliminat.");
            } catch (RuntimeException e) {
                view.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    // ########################## FUNC AUXILIARS
    private String elegirPopularitat() {
        view.missatge("\nPopularitat:");
        view.missatge("1. Baixa");
        view.missatge("2. Mitjana");
        view.missatge("3. Alta");

        int opcio = leerEntero(1, 3);
        switch (opcio) {
            case 1:
                return "baixa";
            case 2:
                return "mitjana";
            case 3:
                return "alta";
            default:
                return "mitjana";
        }
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

    private int leerEntero(int min, int max) {
        while (true) {
            try {
                int valor = Integer.parseInt(sc.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                view.mostrarError("Introdueix un número entre " + min + " i " + max + ":");
            } catch (NumberFormatException e) {
                view.mostrarError("Introdueix un número vàlid:");
            }
        }
    }
}
