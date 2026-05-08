package controller;

import java.util.Scanner;
import model.Escalador;
import DAO.interfaces.EscaladorDAO;
import view.MenuTerminal;
import java.util.List;

public class EscaladorController {

    private final EscaladorDAO escaladorDAO;
    private final MenuTerminal view;
    private final Scanner sc;

    public EscaladorController(EscaladorDAO escaladorDAO) {
        this.escaladorDAO = escaladorDAO;
        this.view = new MenuTerminal();
        this.sc = new Scanner(System.in);
    }

    //crear escalador
    public void crearEscalador() {
        view.missatge("\n=== CREAR ESCALADOR ===");
        
        view.pedirDato("Alias");
        String alias = sc.nextLine();
        
        // Verificar que no existeix
        if (escaladorDAO.findByAlias(alias) != null) {
            view.mostrarError("Error: L'alias '" + alias + "' ja està registrat.");
            return;
        }
        
        view.pedirDato("Nom");
        String nom = sc.nextLine();

        view.pedirDato("Edat");
        int edat = leerEntero();

        String estil = elegirEstil(); 

        Escalador nou = new Escalador(alias, nom, edat, estil);

        try {
            escaladorDAO.create(nou);
            view.mostrarExito("Escalador creat correctament!");
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().equals("EL_ALIAS_YA_EXISTE")) {
                view.mostrarError("Error: L'alias '" + alias + "' ja està registrat.");
            } else {
                view.mostrarError("Error inesperat en crear l'escalador.");
            }
        }
    }

    //modif escalador
    public void modificarEscalador() {
        view.missatge("\n=== MODIFICAR ESCALADOR ===");
        List<Escalador> escaladors = escaladorDAO.findAll();
        
        if (escaladors.isEmpty()) {
            view.mostrarError("No hi ha escaladors.");
            return;
        }
        
        view.missatge("Escaladors disponibles:");
        for (Escalador e : escaladors) {
            view.missatge(e.getId() + ". " + e.getAlias() + " - " + e.getNom());
        }
        
        view.missatge("ID de l'escalador a modificar (0 per sortir):");
        int id = leerEntero();
        if (id == 0) return;
        
        Escalador escalador = escaladorDAO.findById(id);
        if (escalador == null) {
            view.mostrarError("Escalador no trobat.");
            return;
        }
        
        // menu modif
        view.missatge("\nQuè voleu modificar?");
        view.missatge("1. Alias");
        view.missatge("2. Nom");
        view.missatge("3. Edat");
        view.missatge("4. Estil preferit");
        view.missatge("0. Sortir");
        
        int opcio = leerEntero();
        switch (opcio) {
            case 1:
                view.missatge("Nou alias:");
                escalador.setAlias(sc.nextLine());
                break;
            case 2:
                view.missatge("Nou nom:");
                escalador.setNom(sc.nextLine());
                break;
            case 3:
                view.missatge("Nova edat:");
                escalador.setEdat(leerEntero());
                break;
            case 4:
                escalador.setEstilPref(elegirEstil());
                break;
            default:
                return;
        }
        
        try {
            escaladorDAO.update(escalador);
            view.mostrarExito("Escalador modificat.");
        } catch (RuntimeException e) {
            view.mostrarError("Error: " + e.getMessage());
        }
    }

    // list escaladors
    public void llistarEscaladors() {
        view.missatge("\n=== LLISTAR ESCALADORS ===");
        List<Escalador> escaladors = escaladorDAO.findAll();
        
        if (escaladors.isEmpty()) {
            view.missatge("No hi ha escaladors.");
            return;
        }
        
        for (Escalador e : escaladors) {
            view.missatge("---");
            view.missatge("ID: " + e.getId());
            view.missatge("Alias: " + e.getAlias());
            view.missatge("Nom: " + e.getNom());
            view.missatge("Edat: " + e.getEdat());
            view.missatge("Estil preferit: " + e.getEstilPref());
        }
    }

    // delete escalador
    public void eliminarEscalador() {
        view.missatge("\n=== ELIMINAR ESCALADOR ===");
        List<Escalador> escaladors = escaladorDAO.findAll();
        
        if (escaladors.isEmpty()) {
            view.mostrarError("No hi ha escaladors.");
            return;
        }
        
        view.missatge("Escaladors disponibles:");
        for (Escalador e : escaladors) {
            view.missatge(e.getId() + ". " + e.getAlias() + " - " + e.getNom());
        }
        
        view.missatge("ID de l'escalador a eliminar (0 per sortir):");
        int id = leerEntero();
        if (id == 0) return;
        
        view.missatge("Esteu segur? (s/n):");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("s")) {
            try {
                escaladorDAO.delete(id);
                view.mostrarExito("Escalador eliminat.");
            } catch (RuntimeException e) {
                view.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    // aux functions
    
    private String elegirEstil() {
        String estil = "";
        boolean valido = false;

        while (!valido) {
            view.mostrarOpcionesEstil(); 
            int seleccion = leerEntero(); 

switch (seleccion) {
                case 1:
                    estil = "esportiva";
                    valido = true;
                    break;
                case 2:
                    estil = "clàssica";
                    valido = true;
                    break;
                case 3:
                    estil = "gel";
                    valido = true;
                    break;
                default:
                    view.mostrarError("Opció no vàlida. Intenteu de nou.");
            }
        }
        return estil;
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