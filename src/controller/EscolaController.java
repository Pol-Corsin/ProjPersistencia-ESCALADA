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

}
