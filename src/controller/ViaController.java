package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import DAO.interfaces.EscaladorDAO;
import DAO.interfaces.SectorDAO;
import DAO.interfaces.ViaDAO;
import model.Llarg;
import model.Sector;
import model.Via;
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

        // 3. Verificar tipus de via compatible amb el sector
        List<String> tipusExistents = obtenirTipusViesSector(sectorId);
        String tipusVia = elegirTipusVia(tipusExistents);

        // NOM
        view.missatge("Nom de la via:");
        String nom = sc.nextLine();

        // 5. Llargs (segons tipus de via)
        List<Llarg> llargs = new ArrayList<>();
        String grauVia = null;
        if ("esportiva".equals(tipusVia) || "gel".equals(tipusVia)) {
            double llargada = leerDouble("Llargada (" + ("esportiva".equals(tipusVia) ? "5-30" : "15-30") + "m):",
                    "esportiva".equals(tipusVia) ? 5 : 15, 30);
            String grau = elegirGrau(tipusVia);
            grauVia = grau;
            Llarg llarg = new Llarg();
            llarg.setNumeroLlarg(1);
            llarg.setLlargada(llargada);
            llarg.setGrau(grau);
            llargs.add(llarg);
        } else if ("clàssica".equals(tipusVia)) {
            view.missatge("Número de llargs (1-10):");
            int numLlargs = leerEntero(1, 10);
            for (int i = 1; i <= numLlargs; i++) {
                view.missatge("Llarg " + i + ":");
                double llargada = leerDouble("Llargada (15-30m):", 15, 30);
                String grau = elegirGrau(tipusVia);
                if (i == 1)
                    grauVia = grau;
                Llarg llarg = new Llarg();
                llarg.setNumeroLlarg(i);
                llarg.setLlargada(llargada);
                llarg.setGrau(grau);
                llargs.add(llarg);
            }
        }

        // 6. Orientació
        String orientacio = elegirOrientacio();

        // 8. Estat
        String estat = elegirEstat();
        LocalDate dataReobertura = null;
        if (!"Apte".equals(estat)) {
            dataReobertura = leerData("Data de reobertura (DD/MM/AAAA):");
        }

        // 9. Ancoratges (segons tipus de via)
        String ancoratge = elegirAncoratge(tipusVia);

        // 10. Tipus de roca
        String roca = elegirRoca();

        // 11. Restriccions (opcional)
        view.missatge("Restriccions (enter per none):");
        String restriccions = sc.nextLine();
        if (restriccions.trim().isEmpty()) {
            restriccions = null;
        }

        // Crear la via
        Via novaVia = new Via();
        novaVia.setSectorId(sectorId);
        novaVia.setCreadorId(escaladorCreador.getId());
        novaVia.setNom(nom);
        novaVia.setTipus(tipusVia);
        novaVia.setEstat(estat);
        novaVia.setDataReobertura(dataReobertura);
        novaVia.setRoca(roca);
        novaVia.setAncoratge(ancoratge);
        novaVia.setOrientacio(orientacio);
        novaVia.setRestriccions(restriccions);
        novaVia.setGrau(grauVia);

        try {
            viaDAO.create(novaVia);
            view.mostrarExito("Via creada correctament!");

            for (Llarg llarg : llargs) {
                llarg.setViaId(novaVia.getId());
                viaDAO.createLlarg(llarg);
                view.missatge("Llarg " + llarg.getNumeroLlarg() + " creat amb èxit.");
            }

        } catch (RuntimeException e) {
            view.mostrarError("Error en crear la via: " + e.getMessage());
        }

    }

    // ==================== MODIFICAR VIA ====================
    public void modificarVia() {
        view.missatge("\n=== MODIFICAR VIA ===");
        List<Via> vies = viaDAO.findAll();

        if (vies.isEmpty()) {
            view.mostrarError("No hi ha vies.");
            return;
        }

        view.missatge("Vies disponibles:");
        for (Via v : vies) {
            view.missatge(v.getId() + ". " + v.getNom() + " (" + v.getTipus() + ") - " + v.getEstat());
        }

        view.missatge("ID de la via a modificar (0 per sortir):");
        int id = leerEntero();
        if (id == 0)
            return;

        Via via = viaDAO.findById(id);
        if (via == null) {
            view.mostrarError("Via no trobada.");
            return;
        }

        // Menú de modificació
        view.missatge("\nQuè voleu modificar?");
        view.missatge("1. Nom");
        view.missatge("2. Estat");
        view.missatge("3. Data reobertura");
        view.missatge("4. Ancoratge");
        view.missatge("5. Roca");
        view.missatge("6. Orientació");
        view.missatge("7. Restriccions");
        view.missatge("0. Sortir");

        int opcio = leerEntero();
        switch (opcio) {
            case 1:
                view.missatge("Nou nom:");
                via.setNom(sc.nextLine());
                break;
            case 2:
                via.setEstat(elegirEstat());
                if (!"Apte".equals(via.getEstat())) {
                    via.setDataReobertura(leerData("Nova data de reobertura:"));
                }
                break;
            case 3:
                via.setDataReobertura(leerData("Nova data de reobertura:"));
                break;
            case 4:
                via.setAncoratge(elegirAncoratge(via.getTipus()));
                break;
            case 5:
                via.setRoca(elegirRoca());
                break;
            case 6:
                via.setOrientacio(elegirOrientacio());
                break;
            case 7:
                view.missatge("Noves restriccions:");
                via.setRestriccions(sc.nextLine());
                break;
            default:
                return;
        }

        try {
            viaDAO.update(via);
            view.mostrarExito("Via modificada.");
        } catch (RuntimeException e) {
            view.mostrarError("Error: " + e.getMessage());
        }
    }

    // ==================== LLISTAR VIA ====================
    public void llistarVies() {
        view.missatge("\n=== LLISTAR VIES ===");
        List<Via> vies = viaDAO.findAll();

        if (vies.isEmpty()) {
            view.missatge("No hi ha vies.");
            return;
        }

        for (Via v : vies) {
            view.missatge("---");
            view.missatge("ID: " + v.getId());
            view.missatge("Nom: " + v.getNom());
            view.missatge("Tipus: " + v.getTipus());
            view.missatge("Estat: " + v.getEstat());
            view.missatge("Roca: " + v.getRoca());
            view.missatge("Ancoratge: " + v.getAncoratge());
            view.missatge("Orientació: " + v.getOrientacio());
            view.missatge("Creador ID: " + v.getCreadorId());
            if (v.getDataReobertura() != null) {
                view.missatge("Data reobertura: " + v.getDataReobertura());
            }
            if (v.getRestriccions() != null) {
                view.missatge("Restriccions: " + v.getRestriccions());
            }

            // Mostrar llargs
            List<Llarg> llargs = viaDAO.findLlargsByViaId(v.getId());
            for (Llarg l : llargs) {
                view.missatge("  Llarg " + l.getNumeroLlarg() + ": " + l.getLlargada() + "m - " + l.getGrau());
            }
        }
    }

    // ==================== ELIMINAR VIA ====================
    public void eliminarVia() {
        view.missatge("\n=== ELIMINAR VIA ===");
        List<Via> vies = viaDAO.findAll();

        if (vies.isEmpty()) {
            view.mostrarError("No hi ha vies.");
            return;
        }

        view.missatge("Vies disponibles:");
        for (Via v : vies) {
            view.missatge(v.getId() + ". " + v.getNom());
        }

        view.missatge("ID de la via a eliminar (0 per sortir):");
        int id = leerEntero();
        if (id == 0)
            return;

        view.missatge("Esteu segur? (s/n):");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("s")) {
            try {
                viaDAO.delete(id);
                view.mostrarExito("Via eliminada.");
            } catch (RuntimeException e) {
                view.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    // ########################## FUNC AUXILIARS

    private List<String> obtenirTipusViesSector(int sectorId) {
        // Obtenir els tipus de vies que ja existeixen en el sector
        // Si el sector té vies de Gel, només permetrà vies de Gel
        // Si el sector té vies Esportives/Clàssiques, permetrà Esportives/Clàssiques
        // Si està buit, permetrà qualsevol tipus
        List<Via> viesSector = viaDAO.findBySectorId(sectorId);

        if (viesSector.isEmpty()) {
            return null; // Pot triar qualsevol tipus
        }

        boolean teGel = false;
        boolean teAltres = false;

        for (Via v : viesSector) {
            if ("gel".equals(v.getTipus())) {
                teGel = true;
            } else {
                teAltres = true;
            }
        }

        if (teGel && !teAltres) {
            return Arrays.asList("gel");
        } else if (!teGel && teAltres) {
            return Arrays.asList("esportiva", "clàssica");
        } else if (teGel && teAltres) {
            return null; // No hauria de passar segons les regles
        }

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

    private String elegirTipusVia(List<String> tipusPermesos) {
        if (tipusPermesos != null && tipusPermesos.size() == 1) {
            view.missatge("Aquest sector només permet vies de tipus: " + tipusPermesos.get(0));
            return tipusPermesos.get(0);
        }

        view.missatge("\nTipus de via:");
        view.missatge("1. Esportiva");
        view.missatge("2. Clàssica");
        view.missatge("3. Gel");

        int opcio = leerEntero(1, 3);
        switch (opcio) {
            case 1:
                return "esportiva";
            case 2:
                return "clàssica";
            case 3:
                return "gel";
            default:
                return "esportiva";
        }
    }

    private String elegirGrau(String tipusVia) {
        String[] grausEsportiu = { "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+",
                "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", "8c", "8c+", "9a", "9a+", "9b", "9b+",
                "9c", "9c+" };
        String[] grauClassic = { "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+",
                "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b" };
        String[] grauGel = { "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+",
                "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b" };

        String[] graus;
        if ("esportiva".equals(tipusVia)) {
            graus = grausEsportiu;
        } else if ("gel".equals(tipusVia)) {
            graus = grauGel;
        } else {
            graus = grauClassic;
        }

        view.missatge("\nGrau de dificultat:");
        for (int i = 0; i < graus.length; i++) {
            view.missatge((i + 1) + ". " + graus[i]);
        }

        int opcio = leerEntero(1, graus.length);
        return graus[opcio - 1];
    }

    private String elegirOrientacio() {
        String[] orientacions = { "N", "NE", "NO", "SE", "SO", "E", "O", "S" };

        view.missatge("\nOrientació:");
        for (int i = 0; i < orientacions.length; i++) {
            view.missatge((i + 1) + ". " + orientacions[i]);
        }

        int opcio = leerEntero(1, orientacions.length);
        return orientacions[opcio - 1];
    }

    private String elegirEstat() {
        view.missatge("\nEstat:");
        view.missatge("1. Apte");
        view.missatge("2. Construcció");
        view.missatge("3. Tancada");

        int opcio = leerEntero(1, 3);
        switch (opcio) {
            case 1:
                return "Apte";
            case 2:
                return "construcció";
            case 3:
                return "tancada";
            default:
                return "Apte";
        }
    }

    private String elegirAncoratge(String tipusVia) {
        String[] ancoratgesEsportiu = { "spits", "parabolts", "químics" };
        String[] ancoratgesClassic = { "friends", "tascons", "bagues", "pitons", "Tricams", "BigBros", "spits",
                "parabolts", "químics" };
        String[] ancoratgesGel = { "friends", "tascons", "bagues", "pitons", "Tricams", "BigBros" };

        String[] ancoratges;
        if ("esportiva".equals(tipusVia)) {
            ancoratges = ancoratgesEsportiu;
        } else if ("gel".equals(tipusVia)) {
            ancoratges = ancoratgesGel;
        } else {
            ancoratges = ancoratgesClassic;
        }

        view.missatge("\nTipus d'ancoratge:");
        for (int i = 0; i < ancoratges.length; i++) {
            view.missatge((i + 1) + ". " + ancoratges[i]);
        }

        int opcio = leerEntero(1, ancoratges.length);
        return ancoratges[opcio - 1];
    }

    private String elegirRoca() {
        String[] roques = { "conglomerat", "granit", "calcaria", "arenisca", "altres" };

        view.missatge("\nTipus de roca:");
        for (int i = 0; i < roques.length; i++) {
            view.missatge((i + 1) + ". " + roques[i]);
        }

        int opcio = leerEntero(1, roques.length);
        return roques[opcio - 1];
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

    private double leerDouble(String missatge, double min, double max) {
        while (true) {
            try {
                view.missatge(missatge);
                double valor = Double.parseDouble(sc.nextLine());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                view.mostrarError("Introdueix un valor entre " + min + " i " + max + ":");
            } catch (NumberFormatException e) {
                view.mostrarError("Introdueix un número vàlid:");
            }
        }
    }

    private LocalDate leerData(String missatge) {
        while (true) {
            try {
                view.missatge(missatge);
                String input = sc.nextLine();
                String[] parts = input.split("/");
                if (parts.length == 3) {
                    int dia = Integer.parseInt(parts[0]);
                    int mes = Integer.parseInt(parts[1]);
                    int any = Integer.parseInt(parts[2]);
                    return LocalDate.of(any, mes, dia);
                }
                view.mostrarError("Format: DD/MM/AAAA");
            } catch (Exception e) {
                view.mostrarError("Data invàlida. Format: DD/MM/AAAA");
            }
        }
    }
}
