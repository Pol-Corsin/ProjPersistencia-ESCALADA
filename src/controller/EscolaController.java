package controller;

import java.util.Scanner;
import model.Escola;
import DAO.interfaces.EscolaDAO;
import view.MenuTerminal;
import java.util.List;
public class EscolaController {
    
    private final EscolaDAO escolaDAO;
    private final MenuTerminal view;
    private final Scanner sc;

    public EscolaController(EscolaDAO escolaDAO){
        this.escolaDAO = escolaDAO;
        this.view = new MenuTerminal();
        this.sc = new Scanner(System.in);
    }

    public void crearEscola(){
        view.missatge("\n=== CREAR ESCOLA ===");

        view.pedirDato("Nom:");
        String nom = sc.nextLine().trim();

        // Veridicamos que no existe la escuela
        if (escolaDAO.findByNom(nom) != null) {
            view.mostrarError("Error: El nom: " + nom + " ja està registrat");
            return;
        }
        
        view.pedirDato("Poblacio:");
        String poblacio = sc.nextLine().trim();
        while (poblacio.isEmpty()) {
            view.pedirDato("Poblacio");
            poblacio = sc.nextLine().trim();
        }
        


        view.pedirDato("Aproximacio:");
        String aproximacio = sc.nextLine().trim();

        String popularitat = escollirPopularitat();

        view.pedirDato("Restriccions (Opcional): ");
        String restriccio = sc.nextLine().trim();

        if (restriccio.isEmpty()) {
            restriccio = null;
        }

        Escola escolaNova = new Escola(nom,aproximacio,popularitat,restriccio);
        

        try {
            escolaDAO.create(escolaNova);
            
            view.mostrarExito("Escola creat correctament!");
            escolaDAO.addPoblacio(escolaNova.getId(), poblacio);
            
        } catch (RuntimeException e) {
            view.mostrarError("Error inesperat en crear la escola");
        }
    }

    public void modificarEscola(){
        view.missatge("\n=== MODIFICAR ESCOLES ===");
        List<Escola> escoles = escolaDAO.findAll();

        if (escoles.isEmpty()) {
            view.mostrarError("No hi ha esoles");
            return;
        }

        view.missatge("Escoles Disponibles:");
        for (Escola e: escoles){
            view.missatge(e.getId() + " - " + e.getNom());
        }

        view.missatge("Escriu l'ID de la Escola a modificar");
        int id = leerEntero();
        if (id == 0){
            return;
        }

        Escola escola = escolaDAO.findById(id);
        if (escola == null) {
            view.mostrarError("Escola no trobada");
            return;
        }

        //Menu de modificació
        view.missatge("\nQuè voleu modificar?");
        view.missatge("1. Nom");
        view.missatge("2. Aproximació");
        view.missatge("3. Popularitat");
        view.missatge("4. Restriccions");
        view.missatge("0. Sortir");

        escollirModificacio(escola);

        try {
            escolaDAO.update(escola);
            view.mostrarExito("Escola modificada");
        } catch (Exception e) {
            view.mostrarError("Error: " + e.getMessage());
        }
    }

    public void llistarEscoles(){
        view.missatge("\n=== LLISTAR ESCOLES ===");
        List<Escola> escoles = escolaDAO.findAll();

        if (escoles.isEmpty()) {
            view.missatge("No hi ha escoles");
            return;
        }

        for (Escola escola : escoles) {
            view.missatge("---");
            view.missatge("ID: " + escola.getId());
            view.missatge("Nom: " + escola.getNom());
            view.missatge("Aproximacio: " + escola.getAproximacio());
            view.missatge("Popularitat: " + escola.getPopularitat());

            if (escola.getRestriccions() != null) {
                view.missatge("Restriccions: " + escola.getRestriccions());
            }

        }
    }

    public void eliminarEscola(){
        view.missatge("\n=== ELIMINAR ESCOLA ===");
        List<Escola> escoles = escolaDAO.findAll();

        if (escoles.isEmpty()) {
            view.mostrarError("No hi ha escoles.");
            return;
        }

        view.missatge("Escoles disponibles:");
        for (Escola escola : escoles) {
            view.missatge(escola.getId() + " . " + escola.getNom());
        }

        view.missatge("ID de la escola a eliminar (0 per sortir)");
        int id = leerEntero();
        if (id == 0) {
            return;
        }

        view.missatge("Essteu segur? (s/n)");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("s")) {
            try {
                escolaDAO.delete(id);
                view.mostrarError("Escola eliminada");
            } catch (Exception e) {
                view.mostrarError("Error " + e.getMessage());
            }
            
        }
    }


    private String escollirPopularitat() {
        String respuesta = null;  // null en vez de "Nada"
        
        view.missatge("Que popularitat tiene la escola?");
        view.missatge("1) Baixa");
        view.missatge("2) Mitjana");
        view.missatge("3) Alta");
        
        do {
            String input = sc.nextLine().trim();
            
            if (!input.matches("^[0-9]+$")) {
                view.mostrarError("Error: Introdueix un número vàlid");
                continue;
            }
            
            switch (Integer.parseInt(input)) {
                case 1: respuesta = "baixa"; break;
                case 2: respuesta = "mitjana"; break;
                case 3: respuesta = "alta"; break;
                default: view.mostrarError("Error: Escull entre 1, 2 o 3"); break;
            }
            
        } while (respuesta == null);
        
        return respuesta;
    }

    private void escollirModificacio(Escola escola){
        int opcio = -1;

        do {
            opcio = leerEntero();

            switch (opcio) {
                case 1:
                    view.missatge("Nou nom de la escola:");
                    escola.setNom(sc.nextLine().trim());
                    break;
                case 2:
                    view.missatge("Nova aproximacio de la escola:");
                    escola.setAproximacio(sc.nextLine().trim());
                    break;
                case 3:
                    view.missatge("Nova popularitat de la escola:");
                    String popularitat = escollirPopularitat();
                    escola.setPopularitat(popularitat);
                    break;
                case 4:
                    view.missatge("Nova restriccio de la escola:");
                    escola.setRestriccions(sc.nextLine().trim());
                    break;
                case 0:
                    view.missatge("Sortint de la modificació...");
                    break;
                default:
                    view.missatge("Opció no vàlida. Torna a intentar-ho.");
                    break;
            }
        } while (opcio != 0);
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
